package com.smartcampus.springAuth2.controllers.v1;

import com.smartcampus.springAuth2.schemas.LoginRequest;
import com.smartcampus.springAuth2.schemas.SimpleResponse;
import com.smartcampus.springAuth2.schemas.UserResponse;
import com.smartcampus.springAuth2.services.v1.AuthService;
import com.smartcampus.springAuth2.utils.Common;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign_in")
    public ResponseEntity<SimpleResponse<UserResponse>> login(@RequestBody @Valid LoginRequest loginRequest) {
        return Common.createSimpleResponse(authService.login(loginRequest),"Logueado con exito", HttpStatus.OK);
    }

    @GetMapping("/logout")
    public String logout() {
        return "Cerrar sesion";
    }
}
