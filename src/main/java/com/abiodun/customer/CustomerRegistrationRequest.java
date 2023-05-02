package com.abiodun.customer;

public record CustomerRegistrationRequest(
        String name,
        String email,
        Integer age
        ) {
}
