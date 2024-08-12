package com.smartcampus.springAuth2.repositories;

import com.smartcampus.springAuth2.entities.RoleEntity;
import com.smartcampus.springAuth2.entities.UserEntity;
import com.smartcampus.springAuth2.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Boolean existsByFkUserIdAndFkRoleId(UserEntity userId, RoleEntity roleId);
}
