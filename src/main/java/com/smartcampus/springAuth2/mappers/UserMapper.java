package com.smartcampus.springAuth2.mappers;

import com.smartcampus.springAuth2.entities.UserEntity;
import com.smartcampus.springAuth2.schemas.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper {

    @Mapping(source = "id", target = "userId")
    UserEntity toUserEntity(UserResponse userResponse);

    @Mapping(source = "userId", target = "id")
    UserResponse toUserResponse(UserEntity userEntity);
}
