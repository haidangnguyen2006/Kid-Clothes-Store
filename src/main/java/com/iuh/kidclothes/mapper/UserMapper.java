package com.iuh.kidclothes.mapper;

import com.iuh.kidclothes.dto.request.UserCreationRequest;
import com.iuh.kidclothes.dto.request.UserUpdateRequest;
import com.iuh.kidclothes.dto.respone.UserRespone;
import com.iuh.kidclothes.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target="role",ignore = true)
    User toUser(UserCreationRequest request);

    UserRespone toUserRespone(User user);

    List<UserRespone> toUserRespone(List<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target="id", ignore = true)
    @Mapping(target="role", ignore = true)
    @Mapping(target="email", ignore = true)
    @Mapping(target="password", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
