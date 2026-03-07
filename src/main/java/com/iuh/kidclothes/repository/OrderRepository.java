package com.iuh.kidclothes.repository;

import com.iuh.kidclothes.dto.MonthlyRevenueDTO;
import com.iuh.kidclothes.dto.UserSpendingDTO;
import com.iuh.kidclothes.entity.Order;
import com.iuh.kidclothes.enums.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(String userId);

    List<Order> findByStatus(OrderStatus status);

    /**
     * 1. Sắp xếp người dùng theo tổng số tiền mua hàng
     * Nguyễn Hải Đăng
     */
    @Aggregation(pipeline = {
            "{ '$match': { 'status': 'COMPLETED' } }",
            "{ '$group': { '_id': '$userId', 'totalSpent': { '$sum': '$totalAmount' } } }",
            "{ '$project': { 'userId': '$_id', 'totalSpent': 1, '_id': 0 } }",
            "{ '$sort': { 'totalSpent': -1 } }"
    })
    List<UserSpendingDTO> getTopUsersBySpending();

    /**
     * 2. Thống kê / báo cáo doanh thu theo tháng
     */
    @Aggregation(pipeline = {
            "{ '$match': { 'status': 'COMPLETED', '$expr': { '$eq': [{ '$year': '$createdAt' }, ?0] } } }",
            // Gom nhóm theo tháng, tính tổng doanh thu và đếm số lượng đơn
            "{ '$group': { '_id': { '$month': '$createdAt' }, 'totalRevenue': { '$sum': '$totalAmount' }, 'totalOrders': { '$sum': 1 } } }",
            // Định dạng lại đầu ra cho khớp với MonthlyRevenueDTO
            "{ '$project': { 'month': '$_id', 'year': ?0, 'totalRevenue': 1, 'totalOrders': 1, '_id': 0 } }",
            "{ '$sort': { 'month': 1 } }"
    })
    List<MonthlyRevenueDTO> getRevenueByMonth(int year);
}
