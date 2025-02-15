package com.asliutkarsh.springbootsecurityimpl.v1.enums;

public enum SessionPolicy {
    MULTIPLE_SESSIONS, // multiple entries per user
    REPLACE_PREVIOUS_SESSION, // one entry per user
    REVOKE_ALL_SESSIONS, // one entry per user in case if we are previously using MULTIPLE_SESSIONS
    REVOKE_OLDEST_SESSION_IF_MAX_REACHED, // define number of entries per user
    DENY_NEW_SESSION_IF_MAX_REACHED // define number of entries per user
}
