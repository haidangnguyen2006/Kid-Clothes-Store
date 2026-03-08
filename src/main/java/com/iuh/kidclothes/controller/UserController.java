package com.iuh.kidclothes.controller;

import com.iuh.kidclothes.dto.request.UserCreationRequest;
import com.iuh.kidclothes.dto.respone.ApiRespone;
import com.iuh.kidclothes.dto.respone.UserRespone;
import com.iuh.kidclothes.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    ApiRespone<List<UserRespone>> getAllUsers(){
        return ApiRespone.<List<UserRespone>>builder()
                .result(userService.getAll())
                .build();
    }
    @GetMapping("/{email}")
    ApiRespone<UserRespone> getInfo(@PathVariable String email){
        return ApiRespone.<UserRespone>builder()
                .result(userService.getInfo(email))
                .build();
    }

    @GetMapping("/myInfo")
    ApiRespone<UserRespone> getInfo(){
        return ApiRespone.<UserRespone>builder()
                .result(userService.getInfo())
                .build();
    }
}
