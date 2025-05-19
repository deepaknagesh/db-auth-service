package com.districtbusiness.authservice.service.impl;

import com.districtbusiness.authservice.model.Response;
import com.districtbusiness.authservice.model.Signup;
import com.districtbusiness.authservice.repository.AuthenticationRepository;
import com.districtbusiness.authservice.repository.UserRepository;
import com.districtbusiness.authservice.repository.model.Auth;
import com.districtbusiness.authservice.repository.model.User;
import com.districtbusiness.authservice.service.IRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterService implements IRegisterService {

    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Mono<Response<UUID>> signUp(Signup signup) {
        return findUser(signup)
                .switchIfEmpty(Mono.defer(() -> {
                    UUID uuid = UUID.randomUUID();
                    User user = new User(uuid,
                            signup.name(),
                            signup.email(),
                            signup.phoneNumber(),
                            signup.dateOfBirth());
                    return userRepository.save(user)
                            .map(newUser -> new Response<>(newUser.id()));
                }))
                .flatMap(userDetails -> {
                    return this.storeAuth(signup, userDetails);
                });


    }

    private Mono<Response<UUID>> findUser(Signup signup) {
        return userRepository.findByPhoneNumber(signup.phoneNumber())
                .map(registeredUser -> new Response<>(registeredUser.id(), List.of("User already registered.")));
    }

    private Mono<Response<UUID>> storeAuth(Signup signup, Response<UUID> user) {
        if (user.errors().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(signup.password());
            Auth auth = new Auth(encryptedPassword, user.data());
            return authenticationRepository.save(auth)
                    .map(authDetails -> new Response<>(authDetails.userId()));
        }
        return Mono.just(user);
    }
}
