package com.districtbusiness.authservice.repository.model;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@Table("users")
public record User(@Column("id") UUID id,
                   @Column("created_time") LocalDate createdTime,
                   @Column("updated_time") LocalDate updateTime,
                   @Column("name") String name,
                   @Column("email") String email,
                   @Column("phone_number") String phoneNumber,
                   @Column("date_of_birth") LocalDate dateOfBirth) {

    public User(UUID id, String name, String email, String phoneNumber, LocalDate dateOfBirth) {
        this(id, LocalDate.now(), null, name, email, phoneNumber, dateOfBirth);
    }
}
