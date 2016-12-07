package com.pette.server.chattserver.communication;

import com.pette.server.chattserver.chat.ChatHandler;
import com.pette.server.chattserver.chat.ChatMessage;
import com.pette.server.chattserver.persistent.Converter;
import com.pette.server.common.SendMessage;
import com.pette.server.common.UpdateRequest;
import com.pette.server.common.UpdateResponse;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;

public class UpdateRequestRequestHandler implements RequestHandler {
    @Override
    public Object handleRequest(IoSession session, Object receivedData) {
        System.out.println("UpdateRequest");
        UpdateRequest parsedMessage = (UpdateRequest) receivedData;
        return new UpdateResponse(Converter.convertFromPersistentList(ChatHandler.getInstance().getUpdate(parsedMessage), parsedMessage.getChatRoomId()));
    }
}
