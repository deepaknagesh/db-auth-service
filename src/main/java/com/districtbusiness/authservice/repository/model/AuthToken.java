package com.districtbusiness.authservice.repository.model;

import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.util.UUID;

public record AuthToken(@Column("created_time") LocalDate createdTime,
                        @Column("updated_time") LocalDate updateTime,
                        @Column("token") String token,
                        @Column("user_id") UUID userId) {

    public AuthToken(String token, UUID userId) {
        this(LocalDate.now(), null, token, userId);
    }
}
