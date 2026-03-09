package com.iuh.kidclothes.controller;

import com.iuh.kidclothes.dto.request.CartItemRequest;
import com.iuh.kidclothes.dto.respone.ApiRespone;
import com.iuh.kidclothes.dto.respone.CartRespone;
import com.iuh.kidclothes.service.CartService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {

    CartService cartService;

    // Xem giỏ hàng của tôi
    @GetMapping
    public ResponseEntity<ApiRespone<CartRespone>> getMyCart() {
        return ResponseEntity.ok(ApiRespone.<CartRespone>builder()
                .result(cartService.getMyCart())
                .build());
    }

    // Thêm sản phẩm vào giỏ hàng
    @PostMapping("/items")
    public ResponseEntity<ApiRespone<CartRespone>> addToCart(@RequestBody @Valid CartItemRequest request) {
        return ResponseEntity.ok(ApiRespone.<CartRespone>builder()
                .result(cartService.addToCart(request))
                .build());
    }

    // Xóa một sản phẩm cụ thể khỏi giỏ
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<ApiRespone<CartRespone>> removeCartItem(
            @PathVariable String productId,
            @RequestParam String size,
            @RequestParam String color) {
        return ResponseEntity.ok(ApiRespone.<CartRespone>builder()
                .result(cartService.removeItem(productId, size, color))
                .build());
    }

    // Cập nhật số lượng sản phẩm trong giỏ
    @PutMapping("/items/{productId}")
    public ResponseEntity<ApiRespone<CartRespone>> updateCartItem(
            @PathVariable String productId,
            @RequestParam String size,
            @RequestParam String color,
            @RequestParam int quantity) {
        return ResponseEntity.ok(ApiRespone.<CartRespone>builder()
                .result(cartService.updateItemQuantity(productId, size, color, quantity))
                .build());
    }

    // Xóa toàn bộ giỏ hàng
    @DeleteMapping
    public ResponseEntity<ApiRespone<Void>> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok(ApiRespone.<Void>builder()
                .message("Giỏ hàng đã được xóa")
                .build());
    }
}
