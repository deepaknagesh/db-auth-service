package com.districtbusiness.authservice.service;

import com.districtbusiness.authservice.model.Login;
import com.districtbusiness.authservice.model.Response;
import reactor.core.publisher.Mono;

public interface IAuthenticationService {

    Mono<Response<String>> login(Login login);

    Mono<Void> logout(String token);
}
