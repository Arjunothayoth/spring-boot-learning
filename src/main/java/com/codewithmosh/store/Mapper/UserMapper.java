package com.codewithmosh.store.Mapper;

import com.codewithmosh.store.Dtos.ChangePasswordRequest;
import com.codewithmosh.store.Dtos.RegisterUserRequest;
import com.codewithmosh.store.Dtos.UpdateUserRequest;
import com.codewithmosh.store.Dtos.UserDto;
import com.codewithmosh.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);

    User toEntity(RegisterUserRequest request);

    void update(UpdateUserRequest request, @MappingTarget User user);
   }
