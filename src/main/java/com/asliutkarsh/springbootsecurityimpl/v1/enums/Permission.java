package com.asliutkarsh.springbootsecurityimpl.v1.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    USER_CREATE("user:create"),
    USER_READ("user:read"),
    USER_UPDATE("user:update"),
    USER_DELETE("user:delete"),

    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    
    SUPER_ADMIN_CREATE("super_admin:create"),
    SUPER_ADMIN_READ("super_admin:read"),
    SUPER_ADMIN_UPDATE("super_admin:update"),
    SUPER_ADMIN_DELETE("super_admin:delete");

    
    @Getter
    private final String permission;

}
