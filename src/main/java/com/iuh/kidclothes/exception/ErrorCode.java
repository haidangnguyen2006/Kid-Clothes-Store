package com.iuh.kidclothes.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_EXISTED(666, "User existed!!", HttpStatus.BAD_REQUEST),
    USER_UN_EXISTED(777, "User not existed!!", HttpStatus.NOT_FOUND),
    UNCATEGORIZED(999, "Uncategorized Exception!!", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(304, "Uncategorized Exception!!", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(103, "Username must be at least {min} characters!!", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(104, "Password must be at least {min} characters!", HttpStatus.BAD_REQUEST),
    UNAUTHENTICATION(100, "Your account cannot authenticate", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(101, "Your account do not have permission", HttpStatus.FORBIDDEN),
    CANNOT_CREATE_TOKEN(106, "Can not create your token", HttpStatus.BAD_REQUEST),;
    private int code;
    private HttpStatusCode statusCode;
    private String message;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.statusCode = statusCode;
        this.message = message;
    }
}
