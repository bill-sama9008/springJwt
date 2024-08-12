package com.smartcampus.springAuth2.clients.schemas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class RoleClientResponse {
    private Integer vrolId;

    private String vrolNombre;

    private String vrolDescripcion;

    private String vrolTipo;

    private String vrolEstado;

    private String vrolPublico;
}
