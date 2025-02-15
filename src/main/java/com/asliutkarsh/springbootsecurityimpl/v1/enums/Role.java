package com.asliutkarsh.springbootsecurityimpl.v1.enums;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    USER(
        Set.of(
            Permission.USER_READ,
            Permission.USER_CREATE,
            Permission.USER_UPDATE,
            Permission.USER_DELETE
       )
    ),
    ADMIN(
        Set.of(
            Permission.USER_READ,
            Permission.USER_CREATE,
            Permission.USER_UPDATE,
            Permission.USER_DELETE,
            Permission.ADMIN_READ,
            Permission.ADMIN_CREATE,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_DELETE
        )
    ),
    SUPER_ADMIN(
        Set.of(
            Permission.USER_READ,
            Permission.USER_CREATE,
            Permission.USER_UPDATE,
            Permission.USER_DELETE,
            Permission.ADMIN_READ,
            Permission.ADMIN_CREATE,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_DELETE,
            Permission.SUPER_ADMIN_READ,
            Permission.SUPER_ADMIN_CREATE,
            Permission.SUPER_ADMIN_UPDATE,
            Permission.SUPER_ADMIN_DELETE
        )
    );

    
  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    return permissions.stream()
        .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
        .collect(Collectors.toList());
  }

}
