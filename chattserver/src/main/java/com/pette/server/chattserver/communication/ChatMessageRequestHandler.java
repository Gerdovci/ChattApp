package com.pette.server.chattserver.communication;

import com.pette.server.chattserver.chat.ChatHandler;
import com.pette.server.chattserver.chat.ChatMessage;
import com.pette.server.common.SendMessage;
import com.pette.server.common.UpdateRequest;
import org.apache.mina.core.session.IoSession;

public class ChatMessageRequestHandler implements RequestHandler {
    @Override
    public Object handleRequest(IoSession session, Object receivedData) {
        SendMessage request = (SendMessage) receivedData;
        ChatHandler.getInstance().handleNewMessage(new ChatMessage(request.getSenderName(), request.getMessageBody(), request.getTimeStamp(), request.getUUID()), request.getChatroomId());
        return null;
    }
}
