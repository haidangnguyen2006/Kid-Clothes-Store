package com.iuh.kidclothes.repository;

import com.iuh.kidclothes.dto.MonthlyRevenueDTO;
import com.iuh.kidclothes.dto.TopSellingProductDTO;
import com.iuh.kidclothes.dto.UserSpendingDTO;
import com.iuh.kidclothes.dto.respone.ProductRespone;
import com.iuh.kidclothes.entity.Order;
import com.iuh.kidclothes.enums.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(String userId);

    List<Order> findByStatus(OrderStatus status);

    @Aggregation(pipeline = {
            "{ '$match': { 'status': 'COMPLETED' } }",
            "{ '$group': { '_id': '$userId', 'totalSpent': { '$sum': '$totalAmount' } } }",
            "{ '$project': { 'userId': '$_id', 'totalSpent': 1, '_id': 0 } }",
            "{ '$sort': { 'totalSpent': -1 } }"
    })
    List<UserSpendingDTO> getTopUsersBySpending();

    @Aggregation(pipeline = {
            "{ '$match': { 'status': 'COMPLETED', '$expr': { '$eq': [{ '$year': '$createdAt' }, ?0] } } }",
            "{ '$group': { '_id': { '$month': '$createdAt' }, 'totalRevenue': { '$sum': '$totalAmount' }, 'totalOrders': { '$sum': 1 } } }",
            "{ '$project': { 'month': '$_id', 'year': ?0, 'totalRevenue': 1, 'totalOrders': 1, '_id': 0 } }",
            "{ '$sort': { 'month': 1 } }"
    })
    List<MonthlyRevenueDTO> getRevenueByMonth(int year);

    @Aggregation(pipeline = {
            "{ '$match': { 'createdAt': { '$gte': ?0, '$lte': ?1 }, 'status': 'COMPLETED' } }",
            "{ '$unwind': '$items' }",
            "{ '$group': { '_id': '$items.productId', 'productName': { '$first': '$items.productName' }, 'totalQuantitySold': { '$sum': '$items.quantity' } } }",
            "{ '$project': { 'productId': '$_id', 'productName': 1, 'totalQuantitySold': 1, '_id': 0 } }",
            "{ '$sort': { 'totalQuantitySold': -1 } }",
            "{ '$limit': ?2 }"
    })
    List<TopSellingProductDTO> getTopSellingProducts(LocalDateTime startDate, LocalDateTime endDate, int limit);

    @Aggregation(pipeline = {
            "{ '$match': { 'createdAt': { '$gte': ?0, '$lte': ?1 }, 'status': 'COMPLETED' } }",
            "{ '$unwind': '$items' }",
            "{ '$group': { '_id': '$items.productId', 'totalQuantitySold': { '$sum': '$items.quantity' } } }",
            "{ '$sort': { 'totalQuantitySold': -1 } }",
            "{ '$limit': ?2 }",
            "{ '$addFields': { 'productObjId': { '$toObjectId': '$_id' } } }",
            "{ '$lookup': { 'from': 'products', 'localField': 'productObjId', 'foreignField': '_id', 'as': 'productInfo' } }",
            "{ '$unwind': '$productInfo' }",
            "{ '$project': { " +
                    "'id': '$_id', " +
                    "'categoryId': '$productInfo.categoryId', " +
                    "'name': '$productInfo.name', " +
                    "'price': '$productInfo.price', " +
                    "'description': '$productInfo.description', " +
                    "'images': '$productInfo.images', " +
                    "'variants': '$productInfo.variants', " +
                    "'_id': 0 " +
                    "} }"
    })
    List<ProductRespone> getTopSellingProductsFullInfo(LocalDateTime startDate, LocalDateTime endDate, int limit);
}
