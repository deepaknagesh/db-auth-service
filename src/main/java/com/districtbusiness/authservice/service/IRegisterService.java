package com.districtbusiness.authservice.service;

import com.districtbusiness.authservice.model.Response;
import com.districtbusiness.authservice.model.Signup;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IRegisterService {

    Mono<Response<UUID>> signUp(Signup signup);
}
