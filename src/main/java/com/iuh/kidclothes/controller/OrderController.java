package com.iuh.kidclothes.controller;

import com.iuh.kidclothes.dto.MonthlyRevenueDTO;
import com.iuh.kidclothes.dto.TopSellingProductDTO;
import com.iuh.kidclothes.dto.UserSpendingDTO;
import com.iuh.kidclothes.dto.request.OrderCreationRequest;
import com.iuh.kidclothes.dto.respone.*;
import com.iuh.kidclothes.enums.OrderStatus;
import com.iuh.kidclothes.service.OrderService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiRespone<OrderRespone>> createOrder(
            @RequestBody @Valid OrderCreationRequest request) {
        OrderRespone result = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiRespone.<OrderRespone>builder().result(result).build());
    }

    @GetMapping("/my-orders")
    public ResponseEntity<ApiRespone<List<OrderRespone>>> getMyOrders() {
        return ResponseEntity.ok(ApiRespone.<List<OrderRespone>>builder()
                .result(orderService.getMyOrders())
                .build());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiRespone<OrderRespone>> getOrderById(@PathVariable String orderId) {
        return ResponseEntity.ok(ApiRespone.<OrderRespone>builder()
                .result(orderService.getOrderById(orderId))
                .build());
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiRespone<Void>> cancelOrder(@PathVariable String orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok(ApiRespone.<Void>builder()
                .message("Hủy đơn hàng thành công")
                .build());
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<List<OrderRespone>>> getOrdersByStatus(
            @PathVariable OrderStatus status) {
        return ResponseEntity.ok(ApiRespone.<List<OrderRespone>>builder()
                .result(orderService.getOrdersByStatus(status))
                .build());
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<OrderRespone>> updateStatus(
            @PathVariable String orderId,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(ApiRespone.<OrderRespone>builder()
                .result(orderService.updateOrderStatus(orderId, status))
                .build());
    }

    @GetMapping("/statistics/revenue")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<List<MonthlyRevenueDTO>>> getMonthlyRevenue(
            @RequestParam int year) {
        return ResponseEntity.ok(ApiRespone.<List<MonthlyRevenueDTO>>builder()
                .result(orderService.getMonthlyRevenue(year))
                .build());
    }
    @GetMapping("/statistics/orders")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<List<OrderStatisticsRespone>>> getAllOrders(
            @RequestParam(name = "from") LocalDateTime from,
            @RequestParam(name = "to") LocalDateTime to
    ) {
    return ResponseEntity.ok(ApiRespone.<List<OrderStatisticsRespone>>builder()
            .result(orderService.getAllOrders(from, to))
            .build());
    }
    @GetMapping("/statistics/top-users")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<List<UserSpendingDTO>>> getTopUsers() {
        return ResponseEntity.ok(ApiRespone.<List<UserSpendingDTO>>builder()
                .result(orderService.getTopUsersBySpending())
                .build());
    }

    @GetMapping("/statistics/top-products")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<List<TopSellingProductDTO>>> getTopSellingProducts(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(defaultValue = "10") int limit) {

        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);

        return ResponseEntity.ok(ApiRespone.<List<TopSellingProductDTO>>builder()
                .result(orderService.getTopSellingProducts(start, end, limit))
                .build());
    }
}