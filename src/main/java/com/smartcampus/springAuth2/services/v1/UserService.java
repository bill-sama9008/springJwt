package com.smartcampus.springAuth2.services.v1;

import com.smartcampus.springAuth2.entities.UserEntity;
import com.smartcampus.springAuth2.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity findUserByPegeId(String pegeId) {
        return userRepository.findByPegeIdAndIsActive(pegeId, true).orElse(null);
    }

    public void saveUser(UserEntity user) {
        this.userRepository.save(user);
    }
}
