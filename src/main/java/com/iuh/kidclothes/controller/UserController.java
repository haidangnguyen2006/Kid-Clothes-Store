package com.iuh.kidclothes.controller;

import com.iuh.kidclothes.dto.request.UserCreationRequest;
import com.iuh.kidclothes.dto.respone.ApiRespone;
import com.iuh.kidclothes.dto.respone.UserRespone;
import com.iuh.kidclothes.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService userService;
    @PostMapping
    ApiRespone<UserRespone> createUser(@RequestBody @Valid UserCreationRequest request){
        UserRespone result=userService.create(request);

        return ApiRespone.<UserRespone>builder().result(result).build();
    }
}
