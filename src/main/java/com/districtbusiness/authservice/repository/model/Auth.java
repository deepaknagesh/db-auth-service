package com.districtbusiness.authservice.repository.model;

import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.util.UUID;

public record Auth(@Column("created_time") LocalDate createdTime,
                   @Column("updated_time") LocalDate updateTime,
                   @Column("password") String password,
                   @Column("user_id") UUID userId) {

    public Auth(String password, UUID userId) {
        this(LocalDate.now(), null, password, userId);
    }
}
