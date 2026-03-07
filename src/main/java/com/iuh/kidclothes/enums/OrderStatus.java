package com.iuh.kidclothes.enums;

public enum OrderStatus {
    PENDING_PAYMENT, // Chờ thanh toán
    PENDING_DELIVERY,// Chờ giao
    DELIVERING,      // Đang giao
    COMPLETED,       // Hoàn thành
    CANCELED         // Hủy
}
