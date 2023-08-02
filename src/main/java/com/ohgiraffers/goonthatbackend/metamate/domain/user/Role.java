package com.ohgiraffers.goonthatbackend.metamate.domain.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public enum Role {

    ASSOCIATE("ROLE_ASSOCIATE"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private String value;
}