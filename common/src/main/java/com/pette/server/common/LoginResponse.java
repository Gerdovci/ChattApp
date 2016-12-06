package com.pette.server.common;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private boolean isSuccess;

    public LoginResponse(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isSuccess() {
        return isSuccess;
    }
}
