package com.pette.server.chattserver.communication;

import com.pette.server.chattserver.security.AuthenticationInstance;
import com.pette.server.chattserver.security.SecurityValidation;
import com.pette.server.chattserver.security.SessionManager;
import com.pette.server.common.LoginRequest;
import com.pette.server.common.LoginResponse;
import org.apache.mina.core.session.IoSession;

import java.util.Date;

public class LoginRequestRequestHandler implements RequestHandler {
    public Object handleRequest(IoSession session, Object receivedData) {
        System.out.println("LoginRequest");
        LoginRequest message = (LoginRequest) receivedData;
        SessionManager.getInstance().removeSession(session);
        if (SecurityValidation.getInstance().isLoginValid(message.getUsername(), message.getPassword())) {
            AuthenticationInstance auth = new AuthenticationInstance(message.getUsername(), new Date());
            SessionManager.getInstance().addSession(session, auth);
            return new LoginResponse(true);
        }
        return new LoginResponse(false);
    }
}
