package com.districtbusiness.authservice.repository;

import com.districtbusiness.authservice.repository.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserRepository extends ReactiveCrudRepository<User, UUID> {

    Mono<User> findByPhoneNumber(String phoneNumber);

    Mono<User> findByEmail(String email);
}
