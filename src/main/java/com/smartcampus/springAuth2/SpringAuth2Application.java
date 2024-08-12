package com.smartcampus.springAuth2;
import com.smartcampus.springAuth2.repositories.RoleRepository;
import com.smartcampus.springAuth2.repositories.UserRepository;
import com.smartcampus.springAuth2.repositories.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class SpringAuth2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringAuth2Application.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, RoleRepository roleRepository, UserRoleRepository userRoleRepository) {
        return args -> {
//            UserEntity user = UserEntity
//                    .builder()
//                    .username("andres")
//                    .password(this.passwordEncoder.encode("123456"))
//                    .accountNoExpired(true)
//                    .credentialNoExpired(true)
//                    .isAccountNoLocked(true)
//                    .isEnabled(true)
//                    .build();
//
//            userRepository.save(user);
//
//            RoleEntity roleOne = RoleEntity
//                    .builder()
//                    .name("ESTUDIANTE")
//                    .description("Rol de estudiante")
//                    .isEnabled(true)
//                    .build();
//            roleRepository.save(roleOne);
//
//			UserRoleEntity userRoleEntity = UserRoleEntity
//					.builder()
//					.fkRoleId(roleOne)
//					.fkUserId(user)
//					.build();
//			userRoleRepository.save(userRoleEntity);
        };
    }

}
