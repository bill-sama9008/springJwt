package com.smartcampus.springAuth2.repositories;

import com.smartcampus.springAuth2.entities.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {

//    @EntityGraph(attributePaths = {"userRoles"})
//    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByPegeIdAndIsActive(String pegeId, Boolean isActive);
}
