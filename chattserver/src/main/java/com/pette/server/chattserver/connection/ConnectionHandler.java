package com.pette.server.chattserver.connection;

import com.pette.server.chattserver.chat.ChatMessage;
import com.pette.server.chattserver.communication.LoginRequestRequestHandler;
import com.pette.server.chattserver.communication.ChatMessageRequestHandler;
import com.pette.server.chattserver.communication.RequestHandler;
import com.pette.server.chattserver.communication.UpdateRequestRequestHandler;
import com.pette.server.chattserver.security.SessionManager;
import com.pette.server.common.LoginRequest;
import com.pette.server.common.SendMessage;
import com.pette.server.common.UpdateRequest;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import java.util.Date;
import java.util.HashMap;


public class ConnectionHandler extends IoHandlerAdapter {
    HashMap<Class, RequestHandler> logicHashMap;

    public ConnectionHandler() {
        logicHashMap = new HashMap<>();
        logicHashMap.put(LoginRequest.class, new LoginRequestRequestHandler());
        logicHashMap.put(SendMessage.class, new ChatMessageRequestHandler());
        logicHashMap.put(UpdateRequest.class, new UpdateRequestRequestHandler());
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        Object response;
        System.out.println("received:" + message.getClass().getName() + ":" + message.toString());
        if (message == null || message.getClass() == null || logicHashMap.get(message.getClass()) == null) {
            response = "Error noob!";
        } else if (!(SessionManager.getInstance().sessionExistsAndIsLoggedIn(session) || (message instanceof LoginRequest))) {
            response = "Noob wrong login";
        } else {
            response = logicHashMap.get(message.getClass()).handleRequest(session, message);
        }
        if (response != null) {
            session.write(response);
        }
        System.out.println("Message written...");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("new session:" + session.toString());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        SessionManager.getInstance().removeSession(session);
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("IDLE " + session.getIdleCount(status));
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println(cause);
    }
}
