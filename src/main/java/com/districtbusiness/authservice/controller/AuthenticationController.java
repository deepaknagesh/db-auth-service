package com.districtbusiness.authservice.controller;

import com.districtbusiness.authservice.model.Login;
import com.districtbusiness.authservice.model.Response;
import com.districtbusiness.authservice.service.impl.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
//    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public Mono<Response<String>> login(@RequestBody Login login) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(login.username(), login.password())
//        );
        return authenticationService.login(login);
    }

    @PostMapping("logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> logout(@RequestHeader("Authorization") String token) {
        return authenticationService.logout(token);
    }
}
