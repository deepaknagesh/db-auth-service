package com.districtbusiness.authservice.repository;

import com.districtbusiness.authservice.repository.model.Auth;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AuthenticationRepository extends ReactiveCrudRepository<Auth, UUID> {

    Mono<Auth> findByUserId(UUID userId);
}
