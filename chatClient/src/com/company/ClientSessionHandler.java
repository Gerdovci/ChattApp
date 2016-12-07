package com.company;

import com.pette.server.common.LoginRequest;
import com.pette.server.common.LoginResponse;
import com.pette.server.common.SendMessage;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ClientSessionHandler extends IoHandlerAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(ClientSessionHandler.class);

    private boolean finished;

    public ClientSessionHandler() {
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public void sessionOpened(IoSession session) {
        // send summation requests
        session.write(new LoginRequest("sebbe", "1234"));
    }

    @Override
    public void messageReceived(IoSession session, Object message) {
        // server only sends ResultMessage. otherwise, we will have to identify
        // its type using instanceof operator.
        LoginResponse loginResponse = (LoginResponse) message;
        if (loginResponse.isSuccess()) {
            System.out.println("LoginSuccess");
            session.write(new SendMessage("0", Long.toString(new Date().getTime()), "clientTestMessage", "sebbe", new Date()));
        } else {
            // seever returned error code because of overflow, etc.
            LOGGER.warn("Server error, disconnecting...");
            session.closeNow();
            finished = true;
        }
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        cause.printStackTrace();
        session.closeNow();
    }
}
