package com.asliutkarsh.springbootsecurityimpl.v1.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
    
    public static String getAuthenticatedUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
