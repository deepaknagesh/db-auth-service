package com.districtbusiness.authservice.controller;

import com.districtbusiness.authservice.model.Response;
import com.districtbusiness.authservice.model.Signup;
import com.districtbusiness.authservice.service.impl.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping("sign-up")
    public Mono<Response<UUID>> signup(@RequestBody Signup signup) {
        return registerService.signUp(signup);
    }
}
