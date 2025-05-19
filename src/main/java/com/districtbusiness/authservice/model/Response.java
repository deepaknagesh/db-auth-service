package com.districtbusiness.authservice.model;

import java.util.List;

public record Response<T>(T data, List<String> errors) {

    public Response(T data) {
        this(data, List.of());
    }

    public Response(List<String> errors) {
        this(null, errors);
    }
}
