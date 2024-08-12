package com.smartcampus.springAuth2.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUserDto {
    private Long userId;
    private String pegeId;
    private String username;
    private Boolean isActive;
    private String sessionId;
    private String tokenType;
    private List<String> roles;
}
