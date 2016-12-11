package com.pette.server.chattserver.communication;

import com.pette.server.chattserver.chat.ChatHandler;
import com.pette.server.chattserver.persistent.Converter;
import com.pette.server.common.SendMessage;
import com.pette.server.common.UpdateResponse;
import org.apache.mina.core.session.IoSession;

public class ChatMessageRequestHandler implements RequestHandler {
    @Override
    public Object handleRequest(IoSession session, Object receivedData) {
        System.out.println("SendMessage");
        SendMessage request = (SendMessage) receivedData;
        ChatHandler.getInstance().handleNewMessage(Converter.convertToPersistent(request), request.getChatroomId());
        //return new UpdateResponse(Converter.convertFromPersistentList(ChatHandler.getInstance().getUpdateAfterSend(request.getChatroomId(), request.getSenderName()), request.getChatroomId()));
        return null;
    }
}
