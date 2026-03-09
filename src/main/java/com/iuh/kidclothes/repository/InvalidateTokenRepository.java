package com.iuh.kidclothes.repository;

import com.iuh.kidclothes.entity.InvalidatedToken;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvalidateTokenRepository extends MongoRepository<InvalidatedToken,String> {
}
