package com.pette.server.chattserver.security;

import java.util.Date;

public class AuthenticationInstance {
    private String username;
    private Date logintime;

    public AuthenticationInstance(String username, Date logintime) {
        this.username = username;
        this.logintime = logintime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLogintime() {
        return logintime;
    }

    public void setLogintime(Date logintime) {
        this.logintime = logintime;
    }
}
