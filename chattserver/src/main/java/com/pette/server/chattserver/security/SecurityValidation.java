package com.pette.server.chattserver.security;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SecurityValidation {
    private static SecurityValidation instance = null;
    private static ConcurrentHashMap<String, String> validLogins;

    private SecurityValidation() {
        this.validLogins = new ConcurrentHashMap<>();
        this.validLogins.put("sebbe", "1234");
        this.validLogins.put("pette", "1234");
    }

    public static SecurityValidation getInstance() {
        if (instance == null) {
            instance = new SecurityValidation();
        }
        return instance;
    }

    public boolean isLoginValid(String username, String password) {
        if (validLogins.get(username) != null && validLogins.get(username).equals(password)) {
            return true;
        }
        return false;
    }
}
