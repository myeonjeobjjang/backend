package org.myeonjeobjjang.config.security.jwt;

import lombok.Getter;

@Getter
public enum JwtType {
    ACCESS_TOKEN("access"),
    REFRESH_TOKEN("refresh")
    ;
    String data;

    JwtType(String data) {
        this.data = data;
    }
}
