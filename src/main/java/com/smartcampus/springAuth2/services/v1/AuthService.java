package com.smartcampus.springAuth2.services.v1;

import com.smartcampus.springAuth2.clients.apis.LoginClient;
import com.smartcampus.springAuth2.clients.schemas.ClientResponse;
import com.smartcampus.springAuth2.clients.schemas.LoginClientResponse;
import com.smartcampus.springAuth2.clients.schemas.RoleClientResponse;
import com.smartcampus.springAuth2.components.JwtUtil;
import com.smartcampus.springAuth2.dtos.JwtUserDto;
import com.smartcampus.springAuth2.entities.RoleEntity;
import com.smartcampus.springAuth2.entities.UserEntity;
import com.smartcampus.springAuth2.entities.UserRoleEntity;
import com.smartcampus.springAuth2.exceptions.*;
import com.smartcampus.springAuth2.mappers.RoleMapper;
import com.smartcampus.springAuth2.mappers.UserMapper;
import com.smartcampus.springAuth2.schemas.LoginRequest;
import com.smartcampus.springAuth2.schemas.RoleResponse;
import com.smartcampus.springAuth2.schemas.UserResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final LoginClient loginClient;
    private final UserService userService;
    private final RoleService roleService;
    private final UserRoleService userRoleService;
    private final RoleMapper roleMapper;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;

    @Transactional(rollbackFor = Exception.class)
    public UserResponse login(LoginRequest loginRequest) {
        try {
            ClientResponse<LoginClientResponse> response = this.loginClient.loginUser(loginRequest);

            if (response.getCodigo() == HttpStatus.UNAUTHORIZED.value()) {
                throw new UnauthorizedException(response.getMensaje());
            }

            LoginClientResponse loginResponse = response.getValor();

            if (loginResponse == null) {
                throw new InternalServerException(response.getMensaje());
            }

            UserEntity user = this.userService.findUserByPegeId(loginResponse.getPegeId());
            user = this.createUser(user,loginResponse);
            this.userService.saveUser(user);

            List<RoleClientResponse> roles = this.filterUniqueRole(response.getValor().getAuthorities());
            List<RoleResponse> roleResponses = this.saveData(user, roles);

            if (roleResponses.isEmpty()) {
                throw new ConflictException("No tienes los roles para acceder a la plataforma");
            }
            UserResponse userResponse = this.userMapper.toUserResponse(user);
            userResponse.setRoles(roleResponses);
            JwtUserDto userDto = JwtUserDto
                    .builder()
                    .userId(user.getUserId())
                    .username(loginRequest.getLogin())
                    .pegeId(userResponse.getPegeId())
                    .roles(userResponse.getRoles().stream().map(RoleResponse::getName).collect(Collectors.toList()))
                    .isActive(userResponse.getIsActive())
                    .build();
            userResponse.setAccessToken(this.jwtUtil.createAccessToken(userDto));
            userResponse.setRefreshToken(this.jwtUtil.createRefreshToken(userDto));
            return userResponse;

        } catch (FeignException ex) {
            if (ex.status() == 404) {
                throw new RequestTimeoutException("No se ha podido establecer comunicación con el servicio de autenticación");
            }
            if (ex.status() == 200) {
                throw new ForbiddenException("Las credenciales no son válidas");
            }
            throw new InternalServerException("No encontramos conexion con el servidor");
        }
    }

    private List<RoleClientResponse> filterUniqueRole(List<RoleClientResponse> roles) {
        Set<String> roleUnique = new HashSet<>();
        return roles.stream().filter(r -> roleUnique.add(r.getVrolTipo())).collect(Collectors.toList());
    }

    private List<RoleResponse> saveData(UserEntity userEntity, List<RoleClientResponse> roles) {
        List<RoleResponse> roleResponses = new ArrayList<>();
        for (RoleClientResponse role: roles) {
            // Consultar los roles de la base de datos
            RoleEntity roleEntity = this.roleService.getRoleByName(role.getVrolTipo());
            if (roleEntity != null) {
                System.out.println(roleEntity.getName());
                System.out.println(userEntity.getFirstName());
                roleResponses.add(this.roleMapper.toRoleResponse(roleEntity));
                // Guardar solamente la combinacion de usuario y rol que no existen
                if (!this.userRoleService.userHasRole(userEntity,roleEntity)) {
                    UserRoleEntity userRoleEntity = UserRoleEntity.builder()
                            .fkRoleId(roleEntity)
                            .fkUserId(userEntity)
                            .build();
                    this.userRoleService.save(userRoleEntity);
                }
            }
        }
        return roleResponses;
    }

    private UserEntity createUser(UserEntity user,LoginClientResponse clientResponse) {
        if (user != null) {
            user.setFirstName(clientResponse.getPrimernombre());
            user.setSecondName(clientResponse.getSegundonombre());
            user.setSurname(clientResponse.getPrimerapellido());
            user.setSecondSurname(clientResponse.getSegundoapellido());
            user.setPersonalEmail(clientResponse.getCorreoElectronico());
            user.setIsActive(clientResponse.getEstado().equalsIgnoreCase("activo"));
            this.userService.saveUser(user);
        }

        if (user == null) {
            user = UserEntity.builder()
                    .pegeId(clientResponse.getPegeId())
                    .firstName(clientResponse.getPrimernombre())
                    .secondName(clientResponse.getSegundonombre())
                    .surname(clientResponse.getPrimerapellido())
                    .secondSurname(clientResponse.getSegundoapellido())
                    .documentNumber(clientResponse.getIdentificacion())
                    .personalEmail(clientResponse.getCorreoElectronico())
                    .isActive(clientResponse.getEstado().equalsIgnoreCase("activo"))
                    .build();
        }
        return  user;
    }
}
