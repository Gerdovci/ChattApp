package com.pette.server.chattserver.security;

import org.apache.mina.core.session.IoSession;

import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static SessionManager instance = null;
    private static ConcurrentHashMap<IoSession, AuthenticationInstance> openConnections = new ConcurrentHashMap<>();

    private SessionManager() {
        // SingletonClass leave this empty
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public boolean sessionExistsAndIsLoggedIn(IoSession session) {
        if (openConnections.get(session) != null) {
            return true;
        }
        return false;
    }

    public void removeSession(IoSession session) {
        openConnections.remove(session);
    }

    public void addSession(IoSession session, AuthenticationInstance auth) {
        openConnections.put(session, auth);
    }
}
