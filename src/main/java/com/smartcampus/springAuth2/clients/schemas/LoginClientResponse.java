package com.smartcampus.springAuth2.clients.schemas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginClientResponse {

    private String login;

    private String password;

    @NonNull
    private String identificacion;

    @NonNull
    private String correoElectronico;

    @NonNull
    private String pegeId;

    @NonNull
    private String estado;

    @NonNull
    private String primernombre;

    private String segundonombre;

    @NonNull
    private String primerapellido;

    private String segundoapellido;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tokenAccesNew;

    @JsonProperty("listadoroles")
    private List<RoleClientResponse> authorities;
}