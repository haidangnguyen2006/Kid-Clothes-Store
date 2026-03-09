package com.iuh.kidclothes.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iuh.kidclothes.dto.respone.ApiRespone;
import com.iuh.kidclothes.exception.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATION;
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiRespone<?> apiRespone =
                ApiRespone.builder()
                        .code(errorCode.getCode())
                        .result(errorCode.getMessage())
                        .build();
        ObjectMapper objectMapper = new ObjectMapper();

        response.getWriter().write(objectMapper.writeValueAsString(apiRespone));
        response.setStatus(errorCode.getStatusCode().value());
        response.flushBuffer();
    }
}
