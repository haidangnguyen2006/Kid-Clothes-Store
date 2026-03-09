package com.iuh.kidclothes.service;

import com.iuh.kidclothes.dto.request.CartItemRequest;
import com.iuh.kidclothes.dto.respone.CartItemRespone;
import com.iuh.kidclothes.dto.respone.CartRespone;
import com.iuh.kidclothes.entity.Cart;
import com.iuh.kidclothes.entity.CartItem;
import com.iuh.kidclothes.entity.Product;
import com.iuh.kidclothes.entity.User;
import com.iuh.kidclothes.exception.AppException;
import com.iuh.kidclothes.exception.ErrorCode;
import com.iuh.kidclothes.repository.CartRepository;
import com.iuh.kidclothes.repository.ProductRepository;
import com.iuh.kidclothes.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartService {
    CartRepository cartRepository;
    ProductRepository productRepository;
    UserRepository userRepository;

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_UN_EXISTED));
    }

    private Cart getOrCreateCart(String userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = Cart.builder()
                    .userId(userId)
                    .items(new ArrayList<>())
                    .build();
            return cartRepository.save(newCart);
        });
    }

    // 1. Xem giỏ hàng [cite: 120]
    public CartRespone getMyCart() {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user.getId());

        List<CartItemRespone> itemRespones = new ArrayList<>();
        double totalAmount = 0.0;

        for (CartItem item : cart.getItems()) {
            Optional<Product> productOpt = productRepository.findById(item.getProductId());
            if (productOpt.isPresent()) {
                Product product = productOpt.get();
                double subTotal = product.getPrice() * item.getQuantity();
                totalAmount += subTotal;

                itemRespones.add(CartItemRespone.builder()
                        .productId(product.getId())
                        .productName(product.getName())
                        .imageUrl(product.getImages() != null && !product.getImages().isEmpty() ? product.getImages().get(0) : null)
                        .size(item.getSize())
                        .color(item.getColor())
                        .quantity(item.getQuantity())
                        .price(product.getPrice())
                        .subTotal(subTotal)
                        .build());
            }
        }

        return CartRespone.builder()
                .id(cart.getId())
                .userId(user.getId())
                .items(itemRespones)
                .totalAmount(totalAmount)
                .build();
    }

    // 2. Thêm sản phẩm vào giỏ hàng [cite: 62, 109, 110, 111]
    public CartRespone addToCart(CartItemRequest request) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user.getId());

        // Kiểm tra sản phẩm có tồn tại không
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Kiểm tra tồn kho của phân loại (size/color)
        var variantOpt = product.getVariants().stream()
                .filter(v -> v.getSize().equals(request.getSize()) && v.getColor().equals(request.getColor()))
                .findFirst();

        if (variantOpt.isEmpty()) {
            throw new RuntimeException("Phân loại Size/Màu không tồn tại");
        }

        int currentStock = variantOpt.get().getStock();

        // Kiểm tra xem sản phẩm đã có trong giỏ chưa
        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(request.getProductId())
                        && i.getSize().equals(request.getSize())
                        && i.getColor().equals(request.getColor()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            // Nếu đã có -> Cộng dồn số lượng
            CartItem existingItem = existingItemOpt.get();
            int newQuantity = existingItem.getQuantity() + request.getQuantity();

            // Kiểm tra tổng số lượng sau khi cộng dồn có vượt tồn kho không
            if (newQuantity > currentStock) {
                throw new RuntimeException(String.format("Vượt quá số lượng tồn kho. Sản phẩm chỉ còn %d món", currentStock));
            }
            existingItem.setQuantity(newQuantity);
        } else {
            // Nếu chưa có -> Thêm mới
            if (request.getQuantity() > currentStock) {
                throw new RuntimeException(String.format("Vượt quá số lượng tồn kho. Sản phẩm chỉ còn %d món [cite: 115, 179]", currentStock));
            }
            cart.getItems().add(CartItem.builder()
                    .productId(request.getProductId())
                    .size(request.getSize())
                    .color(request.getColor())
                    .quantity(request.getQuantity())
                    .build());
        }

        cartRepository.save(cart);
        return getMyCart(); // Trả về giỏ hàng mới nhất
    }

    // 3. Xóa sản phẩm khỏi giỏ hàng
    public CartRespone removeItem(String productId, String size, String color) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user.getId());

        cart.getItems().removeIf(item ->
                item.getProductId().equals(productId) &&
                        item.getSize().equals(size) &&
                        item.getColor().equals(color)
        );

        cartRepository.save(cart);
        return getMyCart();
    }

    // 4. Cập nhật số lượng sản phẩm trong giỏ hàng
    public CartRespone updateItemQuantity(String productId, String size, String color, int quantity) {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user.getId());

        if (quantity <= 0) {
            throw new RuntimeException("Số lượng phải lớn hơn 0");
        }

        // Kiểm tra tồn kho
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        var variantOpt = product.getVariants().stream()
                .filter(v -> v.getSize().equals(size) && v.getColor().equals(color))
                .findFirst();

        if (variantOpt.isEmpty()) {
            throw new RuntimeException("Phân loại Size/Màu không tồn tại");
        }

        int stock = variantOpt.get().getStock();
        if (quantity > stock) {
            throw new RuntimeException(String.format("Vượt quá số lượng tồn kho. Sản phẩm chỉ còn %d món", stock));
        }

        // Cập nhật số lượng
        Optional<CartItem> itemOpt = cart.getItems().stream()
                .filter(i -> i.getProductId().equals(productId)
                        && i.getSize().equals(size)
                        && i.getColor().equals(color))
                .findFirst();

        if (itemOpt.isPresent()) {
            itemOpt.get().setQuantity(quantity);
            cartRepository.save(cart);
        } else {
            throw new RuntimeException("Sản phẩm này không có trong giỏ hàng");
        }

        return getMyCart();
    }

    // 5. Xóa toàn bộ giỏ hàng
    public void clearCart() {
        User user = getCurrentUser();
        Cart cart = getOrCreateCart(user.getId());
        cart.getItems().clear();
        cartRepository.save(cart);
    }
