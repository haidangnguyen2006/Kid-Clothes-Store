package com.iuh.kidclothes.service;

import com.iuh.kidclothes.dto.request.ChangePasswordRequest;
import com.iuh.kidclothes.dto.request.UserCreationRequest;
import com.iuh.kidclothes.dto.request.UserUpdateRequest;
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
        log.info("Email: "+context.getAuthentication().getName());
        User user = userRepository.findByEmail(context.getAuthentication().getName())
                .orElseThrow(()->new AppException(ErrorCode.USER_UN_EXISTED));

        return userMapper.toUserRespone(user);
    }

    public UserRespone updateMyInfo(UserUpdateRequest request){
        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new AppException(ErrorCode.USER_UN_EXISTED));

        userMapper.updateUser(user, request);

        if(request.getPassword() != null && !request.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user = userRepository.save(user);
        return userMapper.toUserRespone(user);
    }

    @PreAuthorize("hasRole('STAFF')")
    public UserRespone updateUser(String email, UserUpdateRequest request){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new AppException(ErrorCode.USER_UN_EXISTED));

        userMapper.updateUser(user, request);

        if(request.getPassword() != null && !request.getPassword().isBlank()){
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        user = userRepository.save(user);
        return userMapper.toUserRespone(user);
    }

    @PreAuthorize("hasRole('STAFF')")
    public void deleteUser(String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new AppException(ErrorCode.USER_UN_EXISTED));

        userRepository.delete(user);
        log.info("User with email {} has been deleted", email);
    }

    public List<UserRespone> searchUsers(String keyword){
        List<User> users = userRepository.findByFullNameContainingIgnoreCase(keyword);
        return userMapper.toUserRespone(users);
    }

    @PreAuthorize("hasRole('STAFF')")
    public List<UserRespone> getAllUsersByRole(String role){
        Role roleEnum = Role.valueOf(role.toUpperCase());
        List<User> users = userRepository.findByRole(roleEnum);
        return userMapper.toUserRespone(users);
    }

    public void changePassword(ChangePasswordRequest request){
        // Kiểm tra mật khẩu mới và xác nhận mật khẩu
        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new AppException(ErrorCode.PASSWORD_INVALID);
        }

        var context = SecurityContextHolder.getContext();
        String email = context.getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new AppException(ErrorCode.USER_UN_EXISTED));

        // Kiểm tra mật khẩu cũ
        if(!passwordEncoder.matches(request.getOldPassword(), user.getPassword())){
            throw new RuntimeException("Mật khẩu cũ không chính xác");
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info("User {} đã đổi mật khẩu thành công", email);
    }
}
