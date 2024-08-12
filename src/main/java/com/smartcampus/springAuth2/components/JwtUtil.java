package com.smartcampus.springAuth2.components;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.smartcampus.springAuth2.dtos.JwtUserDto;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.smartcampus.springAuth2.utils.Constants.TOKEN_TYPE_ACCESS;
import static com.smartcampus.springAuth2.utils.Constants.TOKEN_TYPE_REFRESH;

@Component
public class JwtUtil {

    @Value("${key.secret.token}")
    private String keyAccessToken;

    private Algorithm algorithm;

    @PostConstruct
    private void initAlgorithms() {
        this.algorithm = Algorithm.HMAC256(keyAccessToken);
    }

    private String getSessionId() {
        UUID uid = UUID.randomUUID();
        return uid.toString();
    }

    public String createAccessToken(JwtUserDto userDto) {
        return JWT.create()
                .withClaim("session_id", this.getSessionId())
                .withClaim("token_type", TOKEN_TYPE_ACCESS)
                .withClaim("roles",userDto.getRoles())
                .withClaim("active", userDto.getIsActive())
                .withClaim("user_id", userDto.getUserId())
                .withClaim("id", userDto.getPegeId())
                .withSubject(userDto.getUsername()) // Usuario
                .withIssuer("SmartCampus") // Quien creo el JSON WEB TOKEN
                .withIssuedAt(new Date()) // Fecha de creación del token
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(10))) // Fecha de expiración del token
                .sign(this.algorithm);
    }

    public String createRefreshToken(JwtUserDto userDto) {
        return JWT.create()
                .withClaim("session_id", this.getSessionId())
                .withClaim("token_type", TOKEN_TYPE_REFRESH)
                .withClaim("roles",userDto.getRoles())
                .withClaim("active", userDto.getIsActive())
                .withClaim("user_id", userDto.getUserId())
                .withClaim("id", userDto.getPegeId())
                .withSubject(userDto.getUsername()) // Usuario
                .withIssuer("SmartCampus") // Quien creo el JSON WEB TOKEN
                .withIssuedAt(new Date()) // Fecha de creación del token
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1))) // Fecha de expiración del token
                .sign(this.algorithm);
    }

    public boolean isValid(String jwt) {
        try {
            JWT.require(algorithm).build().verify(jwt);
            return true;
        } catch (JWTVerificationException ex) {
            return false;
        }
    }

    public JwtUserDto getUser(String jwt) {
        DecodedJWT decodedJWT = JWT.require(algorithm).build().verify(jwt);
        return JwtUserDto.builder()
                .userId(decodedJWT.getClaim("user_id").asLong())
                .sessionId(decodedJWT.getClaim("session_id").asString())
                .tokenType(decodedJWT.getClaim("token_type").asString())
                .pegeId(decodedJWT.getClaim("id").asString())
                .username(decodedJWT.getSubject())
                .isActive(decodedJWT.getClaim("active").asBoolean())
                .roles(decodedJWT.getClaim("roles").asList(String.class))
                .build();
    }
}
