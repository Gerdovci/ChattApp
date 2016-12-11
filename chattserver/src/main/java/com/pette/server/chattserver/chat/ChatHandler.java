package com.pette.server.chattserver.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import com.pette.server.common.UpdateRequest;

public class ChatHandler {
    private LinkedHashMap<String, ChatRoom> chatRooms;
    private static ChatHandler instance = null;

    private ChatHandler() {
        chatRooms = new LinkedHashMap<>();
        ChatRoom chatRoom1 = new ChatRoom(new ArrayList(Arrays.asList("sebbe",
                "pette")));
        chatRooms.put("0", chatRoom1);
    }

    public static ChatHandler getInstance() {
        if (instance == null) {
            instance = new ChatHandler();
        }
        return instance;
    }

    public void handleNewMessage(ChatMessage message, String chatRoomId) {
        ChatRoom room = chatRooms.get(chatRoomId);
        if (room == null) {
            return;
        }
        room.addMessage(message);
    }

    public ArrayList<ChatMessage> getUpdate(UpdateRequest request) {
        if (request.getUUID() != null) {
            return getAfterUUID(request.getChatRoomId(), request.getUUID());
        } else if (request.getIndex() != null && request.getRange() != null) {
            return getLastEntries(request.getChatRoomId(), request.getIndex(), request.getRange());
        } else {
            return getSlimUpdate(request.getChatRoomId(), request.getUsername());
        }
    }

    public ArrayList<ChatMessage> getUpdateAfterSend(String chatRoomId, String userName) {
        return getSlimUpdate(chatRoomId, userName);
    }

    /**
     * Returns a list of messages that have not yet been sent to the user.
     *
     * @param chatRoomId
     * @param userName
     * @return
     */
    private ArrayList<ChatMessage> getSlimUpdate(String chatRoomId, String userName) {
        if (chatRooms.get(chatRoomId) != null) {
            return chatRooms.get(chatRoomId).getMessagesForUserAndUpdateIndex(userName);
        }
        return new ArrayList<>();
    }

    /**
     * Returns messages from the list within a given range.
     *
     * @param chatRoomId
     * @param index
     * @param range
     * @return
     */
    private ArrayList<ChatMessage> getLastEntries(String chatRoomId, int index, int range) {
        ChatRoom room = chatRooms.get(chatRoomId);
        return room.getLastMessages(index, range);
    }

    /**
     * Returns all messages after a certain UUID, including the message with the specified UUID.
     *
     * @param chatRoomId
     * @param UUID
     * @return
     */
    private ArrayList<ChatMessage> getAfterUUID(String chatRoomId, String UUID) {
        ChatRoom room = chatRooms.get(chatRoomId);
        return room.getSinceUUID(UUID);
    }
}
