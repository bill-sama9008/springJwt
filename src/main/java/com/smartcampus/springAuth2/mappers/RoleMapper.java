package com.smartcampus.springAuth2.mappers;

import com.smartcampus.springAuth2.entities.RoleEntity;
import com.smartcampus.springAuth2.schemas.RoleResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(source = "roleId", target = "id")
    RoleResponse toRoleResponse(RoleEntity roleEntity);

    @Mapping(source = "roleId", target = "id")
    List<RoleResponse> toListRoleResponse(List<RoleEntity> roleEntities);

}
