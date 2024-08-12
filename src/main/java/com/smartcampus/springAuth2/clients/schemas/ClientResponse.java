package com.smartcampus.springAuth2.clients.schemas;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ClientResponse<T> {
    private Integer codigo;
    private String mensaje;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T valor;
}
