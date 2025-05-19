package com.districtbusiness.authservice.service.impl;

import com.districtbusiness.authservice.model.Login;
import com.districtbusiness.authservice.model.Response;
import com.districtbusiness.authservice.repository.AuthenticationRepository;
import com.districtbusiness.authservice.repository.AuthenticationTokenRepository;
import com.districtbusiness.authservice.repository.UserRepository;
import com.districtbusiness.authservice.repository.model.AuthToken;
import com.districtbusiness.authservice.service.IAuthenticationService;
import com.districtbusiness.authservice.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationRepository authenticationRepository;
    private final JwtService jwtService;
    private final AuthenticationTokenRepository authenticationTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Response<String>> login(Login login) {
        return userRepository.findByEmail(login.username())
                .flatMap(user -> {
                    return authenticationTokenRepository.findByUserId(user.id());
                })
                .map(authToken -> new Response<>(authToken.token()))
                .switchIfEmpty(Mono.defer(() -> this.createToken(login)));

    }

    @Override
    public Mono<Void> logout(String token) {
        String username = jwtService.extractUsername(token);
        return userRepository.findByEmail(username)
                .flatMap(user -> {
                    return authenticationTokenRepository.findByUserId(user.id())
                            .map(userDetails -> {
                                System.out.println(userDetails);
                                return userDetails;
                            });
                })
                .flatMap(authToken -> {
                    System.out.println(authToken);
                    return authenticationTokenRepository.deleteByUserId(authToken.userId());
                });
    }

    private Mono<Response<String>> createToken(Login login) {
        return userRepository.findByEmail(login.username())
                .flatMap(user -> {
                    return authenticationRepository.findByUserId(user.id());
                })
                .filter(auth -> passwordEncoder.matches(login.password(), auth.password()))
                .map(auth -> {
                    String token = jwtService.generateToken(login.username());
                    return new AuthToken(token, auth.userId());
                })
                .flatMap(authenticationTokenRepository::save)
                .map(authToken -> new Response<>(authToken.token()))
                .switchIfEmpty(Mono.just(new Response<>(List.of("Invalid user"))));
    }
}
