package com.smartcampus.springAuth2.services.v1;

import com.smartcampus.springAuth2.entities.RoleEntity;
import com.smartcampus.springAuth2.entities.UserEntity;
import com.smartcampus.springAuth2.entities.UserRoleEntity;
import com.smartcampus.springAuth2.repositories.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository repository;

    public void save(UserRoleEntity userRoleEntity) {
        this.repository.save(userRoleEntity);
    }

    public Boolean userHasRole(UserEntity userId, RoleEntity roleId) {
        return this.repository.existsByFkUserIdAndFkRoleId(userId,roleId);
    }
}
