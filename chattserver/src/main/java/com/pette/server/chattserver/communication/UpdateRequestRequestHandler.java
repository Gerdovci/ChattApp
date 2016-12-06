package com.pette.server.chattserver.communication;

import com.pette.server.chattserver.chat.ChatHandler;
import com.pette.server.chattserver.chat.ChatMessage;
import com.pette.server.common.SendMessage;
import com.pette.server.common.UpdateRequest;
import com.pette.server.common.UpdateResponse;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;

public class UpdateRequestRequestHandler implements RequestHandler {
    @Override
    public Object handleRequest(IoSession session, Object receivedData) {
        UpdateRequest parsedMessage = (UpdateRequest) receivedData;
        ArrayList<SendMessage> messages = new ArrayList<>();
        return new UpdateResponse(chatMessageListToSendMessageList(ChatHandler.getInstance()
                        .getUpdate(parsedMessage.getChatRoomId(),
                                parsedMessage.getUsername()),
                parsedMessage.getChatRoomId()));
    }

    private static SendMessage chatMessageToSendMessage(ChatMessage message, String chatroomId) {
        return new SendMessage(chatroomId, message.getUUID(), message.getMessageBody(), message.getSenderName(), message.getTimeStamp());
    }

    private static ArrayList<SendMessage> chatMessageListToSendMessageList(ArrayList<ChatMessage> messages, String chatroomId) {
        ArrayList<SendMessage> returnMessages = new ArrayList<>();
        for (ChatMessage message : messages) {
            returnMessages.add(chatMessageToSendMessage(message, chatroomId));
        }
        return returnMessages;
    }
}
