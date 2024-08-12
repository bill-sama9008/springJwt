package com.smartcampus.springAuth2.schemas;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String pegeId;
    private String firstName;
    private String secondName;
    private String surname;
    private String secondSurname;
    private String documentNumber;
    private String personalEmail;
    private Boolean isActive;
    private String accessToken;
    private String refreshToken;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<RoleResponse> roles;
}
