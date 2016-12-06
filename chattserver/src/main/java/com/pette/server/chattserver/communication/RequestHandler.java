package com.pette.server.chattserver.communication;

import org.apache.mina.core.session.IoSession;

public interface RequestHandler {
    Object handleRequest(IoSession session, Object receivedData);
}
