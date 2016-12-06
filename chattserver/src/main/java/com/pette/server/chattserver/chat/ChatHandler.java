package com.pette.server.chattserver.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class ChatHandler {
    private ConcurrentHashMap<String, ChatRoom> chatRooms;
    private static ChatHandler instance = null;

    private ChatHandler() {
        chatRooms = new ConcurrentHashMap<>();
        ChatRoom chatRoom1 = new ChatRoom(new ArrayList(Arrays.asList("sebbe", "pette")));
        chatRooms.put("0", chatRoom1);
    }

    public static ChatHandler getInstance() {
        if (instance == null) {
            instance = new ChatHandler();
        }
        return instance;
    }

    public void handleNewMessage(ChatMessage message, String chatroomId) {
        ChatRoom room = chatRooms.get(chatroomId);
        if (room == null) {
            return;
        }
        room.addMessage(message);
    }

    public ArrayList<ChatMessage> getUpdate(String chatRoomId, String userName) {
        if (chatRooms.get(chatRoomId) != null) {
            return chatRooms.get(chatRoomId).getMessagesForUserAndUpdateIndex(userName);
        }
        return null;
    }
}
