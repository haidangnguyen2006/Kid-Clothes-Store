package com.iuh.kidclothes.repository;

import com.iuh.kidclothes.entity.User;
import com.iuh.kidclothes.enums.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<User> findByFullNameContainingIgnoreCase(String fullName);

    List<User> findByRole(Role role);
}
