package org.example.identityservice.mapper;

import org.example.identityservice.dto.request.UserCreationRequest;
import org.example.identityservice.dto.request.UserUpdateRequest;
import org.example.identityservice.dto.response.UserResponse;
import org.example.identityservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
    User toUser(UserCreationRequest request);
    
//    @Mapping(source = "firstName", target = "lastName")
    UserResponse toUserResponse(User user);
}
