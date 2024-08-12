package com.smartcampus.springAuth2.controllers.v1;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
public class UserController {

        @GetMapping
        @PreAuthorize("hasRole('ESTUDIANTE')")
        public String findAll() {
            return "Soy todos los usuarios";
        }
}
