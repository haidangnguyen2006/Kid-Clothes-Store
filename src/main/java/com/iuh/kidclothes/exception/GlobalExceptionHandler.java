package com.iuh.kidclothes.exception;

import com.iuh.kidclothes.dto.respone.ApiRespone;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
   /* @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiRespone> handlingException(Exception exception){
        ErrorCode errorCode=ErrorCode.UNCATEGORIZED;
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(
                        ApiRespone.builder()
                                .code(errorCode.getCode())
                                .result(errorCode.getMessage())
                                .build()

        );
    }*/

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiRespone> handlingAppException(AppException exception){
        ErrorCode errorCode=exception.getErrorCode();
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(
                        ApiRespone.builder()
                                .code(errorCode.getCode())
                                .result(errorCode.getMessage())
                                .build()

                );
    }
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiRespone> handlingAccessDeniedException(AccessDeniedException exception) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(
                        ApiRespone.builder()
                                .code(errorCode.getCode())
                                .result(errorCode.getMessage())
                                .build());
    }
}
