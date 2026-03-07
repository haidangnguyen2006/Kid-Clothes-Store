package com.iuh.kidclothes.entity;

import com.iuh.kidclothes.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Document(collection = "users")
public class User {
    @Id
    String id;
    String fullName;
    String email;
    String password;
    String phone;
    String address;
    Role role;
}
