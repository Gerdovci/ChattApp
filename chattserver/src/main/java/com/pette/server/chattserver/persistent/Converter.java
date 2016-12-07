package com.pette.server.chattserver.persistent;

import com.pette.server.chattserver.chat.ChatMessage;
import com.pette.server.common.SendMessage;

import java.util.ArrayList;

public class Converter {
    public static SendMessage convertFromPersistent(ChatMessage message, String chatroomId) {
        return new SendMessage(chatroomId,
                message.getUUID(),
                message.getMessageBody(),
                message.getSenderName(),
                message.getTimeStamp()
        );
    }

    public static ArrayList<SendMessage> convertFromPersistentList(ArrayList<ChatMessage> messages, String chatroomId) {
        ArrayList<SendMessage> returnMessages = new ArrayList<>();
        for (ChatMessage message : messages) {
            returnMessages.add(Converter.convertFromPersistent(message, chatroomId));
        }
        return returnMessages;
    }

    public static ChatMessage convertToPersistent(SendMessage message) {
        return new ChatMessage(
                message.getSenderName(),
                message.getMessageBody(),
                message.getTimeStamp(),
                message.getUUID()
        );
    }
}
