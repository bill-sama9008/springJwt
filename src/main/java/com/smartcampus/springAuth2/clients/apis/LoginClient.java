package com.smartcampus.springAuth2.clients.apis;

import com.smartcampus.springAuth2.clients.schemas.ClientResponse;
import com.smartcampus.springAuth2.clients.schemas.LoginClientResponse;
import com.smartcampus.springAuth2.schemas.LoginRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "LoginUser", url = "${smartcampus.service.url.login}")
public interface LoginClient {
    @PostMapping(value = "/login/api/authentication", consumes = {MediaType.APPLICATION_JSON_VALUE})
    ClientResponse<LoginClientResponse> loginUser(@RequestBody LoginRequest loginRequest);

//    @PostMapping(value = "/login/api/olvideClave", consumes = {MediaType.APPLICATION_JSON_VALUE})
//    ClientResponse<Object> recoveryAccount(@RequestBody ResetPasswordRequest resetPasswordRequest);
}
