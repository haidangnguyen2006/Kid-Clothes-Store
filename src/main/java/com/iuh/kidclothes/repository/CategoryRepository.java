package com.iuh.kidclothes.repository;

import com.iuh.kidclothes.entity.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface CategoryRepository extends MongoRepository<Category,String> {
}
