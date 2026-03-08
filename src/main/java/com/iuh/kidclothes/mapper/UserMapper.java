package com.iuh.kidclothes.mapper;

import com.iuh.kidclothes.dto.request.UserCreationRequest;
import com.iuh.kidclothes.dto.respone.UserRespone;
import com.iuh.kidclothes.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target="role",ignore = true)
    User toUser(UserCreationRequest request);

    UserRespone toUserRespone(User user);

    List<UserRespone> toUserRespone(List<User> users);
}
