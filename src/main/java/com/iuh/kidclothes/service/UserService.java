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
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @PreAuthorize("hasRole('STAFF')")
    public List<UserRespone> getAll(){
        List<User> users=userRepository.findAll();
        return userMapper.toUserRespone(users);
    }

    @PostAuthorize("returnObject.email==authentication.name or hasRole('STAFF')")
    public UserRespone getInfo(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new AppException(ErrorCode.USER_UN_EXISTED));

        return userMapper.toUserRespone(user);
    }

    public UserRespone getInfo(){
        var context= SecurityContextHolder.getContext();

        User user = userRepository.findByEmail(context.getAuthentication().getName())
                .orElseThrow(()->new AppException(ErrorCode.USER_UN_EXISTED));

        return userMapper.toUserRespone(user);
    }
}
