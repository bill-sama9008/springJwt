package com.smartcampus.springAuth2.services.v1;

import com.smartcampus.springAuth2.entities.RoleEntity;
import com.smartcampus.springAuth2.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleEntity getRoleByName(String name) {
        return this.roleRepository.findByName(name).orElse(null);
    }
}
