package com.iuh.kidclothes.service;

import com.iuh.kidclothes.dto.request.UserCreationRequest;
import com.iuh.kidclothes.dto.respone.UserRespone;
import com.iuh.kidclothes.entity.User;
import com.iuh.kidclothes.enums.Role;
import com.iuh.kidclothes.exception.AppException;
import com.iuh.kidclothes.exception.ErrorCode;
import com.iuh.kidclothes.mapper.UserMapper;
import com.iuh.kidclothes.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public UserRespone create(UserCreationRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user=userMapper.toUser(request);
        user.setRole(Role.CUSTOMER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user=userRepository.save(user);
        return userMapper.toUserRespone(user);
    }
}
