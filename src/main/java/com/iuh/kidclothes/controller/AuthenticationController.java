package com.iuh.kidclothes.controller;

import com.iuh.kidclothes.dto.request.AuthenticationRequest;
import com.iuh.kidclothes.dto.respone.ApiRespone;
import com.iuh.kidclothes.dto.respone.AuthenticationRespone;
import com.iuh.kidclothes.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authService;

    @PostMapping("/signin")
    ApiRespone<AuthenticationRespone> signin(@RequestBody @Valid AuthenticationRequest request){
        AuthenticationRespone result= authService.signin(request);
        return ApiRespone.<AuthenticationRespone>builder()
                .result(result)
                .build();
    }

}
