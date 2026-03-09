package com.iuh.kidclothes.service;

import com.iuh.kidclothes.dto.MonthlyRevenueDTO;
import com.iuh.kidclothes.dto.TopSellingProductDTO;
import com.iuh.kidclothes.dto.UserSpendingDTO;
import com.iuh.kidclothes.dto.request.OrderCreationRequest;
import com.iuh.kidclothes.dto.respone.*;
import com.iuh.kidclothes.entity.*;
import com.iuh.kidclothes.enums.OrderStatus;
import com.iuh.kidclothes.enums.PaymentMethod;
import com.iuh.kidclothes.exception.AppException;
import com.iuh.kidclothes.exception.ErrorCode;
import com.iuh.kidclothes.mapper.OrderMapper;
import com.iuh.kidclothes.repository.CartRepository;
import com.iuh.kidclothes.repository.OrderRepository;
import com.iuh.kidclothes.repository.ProductRepository;
import com.iuh.kidclothes.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {
    OrderRepository orderRepository;
    CartRepository cartRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    OrderMapper orderMapper;
    ProductService productService;

    @Transactional
    public OrderRespone createOrder(OrderCreationRequest request) {
        // 1. Lấy thông tin User hiện tại
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_UN_EXISTED));

        // 2. Lấy giỏ hàng của User
        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Giỏ hàng trống"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Không có sản phẩm trong giỏ hàng");
        }

        // 3. Xử lý Items & Tính tổng tiền & Trừ tồn kho
        double totalAmount = 0.0;
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getItems()) {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));

            // THỰC HIỆN TRỪ TỒN KHO
            // Nếu không đủ hàng, hàm reduceStock sẽ ném ra RuntimeException và hủy toàn bộ transaction
            productService.reduceStock(product.getId(), cartItem.getSize(), cartItem.getColor(), cartItem.getQuantity());

            double price = product.getPrice();

            // Tính tổng tiền dồn
            totalAmount += price * cartItem.getQuantity();

            orderItems.add(OrderItem.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .size(cartItem.getSize())
                    .color(cartItem.getColor())
                    .quantity(cartItem.getQuantity())
                    .price(price)
                    .build());
        }

        double shippingFee = 30000.0; //mặc định là 30k

        // 4. Tạo Đơn Hàng [cite: 66, 125]
        Order order = Order.builder()
                .userId(user.getId())
                .receiverName(request.getReceiverName())
                .receiverPhone(request.getReceiverPhone())
                .shippingAddress(request.getShippingAddress())
                .items(orderItems)
                .totalAmount(totalAmount + shippingFee)
                .shippingFee(shippingFee)
                .paymentMethod(request.getPaymentMethod())
                .status(request.getPaymentMethod() == PaymentMethod.COD ? OrderStatus.PENDING_DELIVERY : OrderStatus.PENDING_PAYMENT)
                .createdAt(LocalDateTime.now())
                .build();

        order = orderRepository.save(order);

        // 5. Xóa giỏ hàng sau khi đặt thành công
        cart.getItems().clear();
        cartRepository.save(cart);

        return orderMapper.toOrderRespone(order);
    }

    public List<OrderRespone> getMyOrders() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_UN_EXISTED));

        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orderMapper.toOrderResponeList(orders);
    }

    public OrderRespone getOrderById(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        // Kiểm tra quyền: chỉ user sở hữu hoặc STAFF mới xem được
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_UN_EXISTED));

        if (!order.getUserId().equals(currentUser.getId()) && !currentUser.getRole().name().equals("STAFF")) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        return orderMapper.toOrderRespone(order);
    }

    public void cancelOrder(String orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_UN_EXISTED));

        // Chỉ user sở hữu hoặc STAFF mới có thể hủy
        if (!order.getUserId().equals(currentUser.getId()) && !currentUser.getRole().name().equals("STAFF")) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }

        // Chỉ có thể hủy nếu chưa hoàn thành hoặc đã nhận hàng
        if (order.getStatus() == OrderStatus.COMPLETED) {
            throw new RuntimeException("Không thể hủy đơn hàng đã hoàn thành");
        }

        // Nếu đã trừ tồn kho, cần cộng lại
        if (order.getStatus() != OrderStatus.PENDING_PAYMENT) {
            for (OrderItem item : order.getItems()) {
                Product product = productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOTFOUND));

                // Cộng lại tồn kho
                for (var variant : product.getVariants()) {
                    if (variant.getSize().equals(item.getSize()) && variant.getColor().equals(item.getColor())) {
                        variant.setStock(variant.getStock() + item.getQuantity());
                        break;
                    }
                }
                productRepository.save(product);
            }
        }

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
        log.info("Đơn hàng {} đã bị hủy", orderId);
    }

    @PreAuthorize("hasRole('STAFF')")
    public List<OrderRespone> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findByStatus(status);
        return orderMapper.toOrderResponeList(orders);
    }

    @PreAuthorize("hasRole('STAFF')")
    public OrderRespone updateOrderStatus(String orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));

        order.setStatus(newStatus);
        return orderMapper.toOrderRespone(orderRepository.save(order));
    }

    @PreAuthorize("hasRole('STAFF')")
    public List<MonthlyRevenueDTO> getMonthlyRevenue(int year) {
        return orderRepository.getRevenueByMonth(year);
    }

    @PreAuthorize("hasRole('STAFF')")
    public List<UserSpendingDTO> getTopUsersBySpending() {
        return orderRepository.getTopUsersBySpending();
    }

    @PreAuthorize("hasRole('STAFF')")
    public List<TopSellingProductDTO> getTopSellingProducts(LocalDateTime start, LocalDateTime end, int limit) {
        return orderRepository.getTopSellingProducts(start, end, limit);
    }
}