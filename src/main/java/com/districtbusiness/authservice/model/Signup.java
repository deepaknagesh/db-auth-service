package com.districtbusiness.authservice.model;

import java.time.LocalDate;

public record Signup(String name,
                     String phoneNumber,
                     String email,
                     LocalDate dateOfBirth,
                     String password) {
}
