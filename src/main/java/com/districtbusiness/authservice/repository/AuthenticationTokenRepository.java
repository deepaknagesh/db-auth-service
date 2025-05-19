package com.districtbusiness.authservice.repository;

import com.districtbusiness.authservice.repository.model.AuthToken;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AuthenticationTokenRepository extends ReactiveCrudRepository<AuthToken, UUID> {
    Mono<AuthToken> findByUserId(UUID userId);

    Mono<Void> deleteByUserId(UUID userId);
}
