package com.iuh.kidclothes.controller;

import com.iuh.kidclothes.dto.request.UserCreationRequest;
import com.iuh.kidclothes.dto.request.UserUpdateRequest;
import com.iuh.kidclothes.dto.respone.ApiRespone;
import com.iuh.kidclothes.dto.respone.UserRespone;
import com.iuh.kidclothes.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiRespone<UserRespone>> createUser(
            @RequestBody @Valid UserCreationRequest request){
        UserRespone result = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiRespone.<UserRespone>builder().result(result).build());
    }

    @GetMapping
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<List<UserRespone>>> getAllUsers(){
        return ResponseEntity.ok(ApiRespone.<List<UserRespone>>builder()
                .result(userService.getAll())
                .build());
    }

    @GetMapping("/myInfo")
    public ResponseEntity<ApiRespone<UserRespone>> getMyInfo(){
        return ResponseEntity.ok(ApiRespone.<UserRespone>builder()
                .result(userService.getInfo())
                .build());
    }

    @GetMapping("/{email}")
    public ResponseEntity<ApiRespone<UserRespone>> getUserByEmail(@PathVariable String email){
        return ResponseEntity.ok(ApiRespone.<UserRespone>builder()
                .result(userService.getInfo(email))
                .build());
    }

    @PutMapping("/myInfo")
    public ResponseEntity<ApiRespone<UserRespone>> updateMyInfo(
            @RequestBody @Valid UserUpdateRequest request){
        UserRespone result = userService.updateMyInfo(request);
        return ResponseEntity.ok(ApiRespone.<UserRespone>builder().result(result).build());
    }

    @PutMapping("/{email}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<UserRespone>> updateUser(
            @PathVariable String email,
            @RequestBody @Valid UserUpdateRequest request){
        UserRespone result = userService.updateUser(email, request);
        return ResponseEntity.ok(ApiRespone.<UserRespone>builder().result(result).build());
    }

    @DeleteMapping("/{email}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<Void>> deleteUser(@PathVariable String email){
        userService.deleteUser(email);
        return ResponseEntity.ok(ApiRespone.<Void>builder()
                .message("User deleted successfully")
                .build());
    }

    @GetMapping("/search")
    public ResponseEntity<ApiRespone<List<UserRespone>>> searchUsers(
            @RequestParam String keyword){
        List<UserRespone> result = userService.searchUsers(keyword);
        return ResponseEntity.ok(ApiRespone.<List<UserRespone>>builder().result(result).build());
    }

    @GetMapping("/role/{role}")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<ApiRespone<List<UserRespone>>> getUsersByRole(
            @PathVariable String role){
        List<UserRespone> result = userService.getAllUsersByRole(role);
        return ResponseEntity.ok(ApiRespone.<List<UserRespone>>builder().result(result).build());
    }
}

