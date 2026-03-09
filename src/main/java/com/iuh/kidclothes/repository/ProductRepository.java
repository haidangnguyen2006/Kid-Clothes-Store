package com.iuh.kidclothes.repository;


import com.iuh.kidclothes.dto.TopSellingProductDTO;
import com.iuh.kidclothes.entity.Product;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByNameContainingIgnoreCase(String keyword);

    List<Product> findByCategoryId(String categoryId);

    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

}