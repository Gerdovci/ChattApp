package com.pette.server.chattserver.chat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

    private ArrayList<ChatMessage> getSlimUpdate(String chatRoomId,
	    String userName) {
	if (chatRooms.get(chatRoomId) != null) {
	    return chatRooms.get(chatRoomId).getMessagesForUserAndUpdateIndex(
		    userName);
	}
	return new ArrayList<>();
    }

    public ArrayList<ChatMessage> getUpdate(UpdateRequest request) {
	if (request.getPuzzle() != null) {
	    return getAfterTimeStampEntries(request.getChatRoomId(),
		    request.getPuzzle());
	} else if (request.getIndex() != null && request.getRange() != null) {
	    return getLastEntries(request.getChatRoomId(), request.getIndex(),
		    request.getRange());
	} else {
	    return getSlimUpdate(request.getChatRoomId(), request.getUsername());
	}
    }

    private ArrayList<ChatMessage> getLastEntries(String chatRoomId, int index,
	    int range) {
	ChatRoom room = chatRooms.get(chatRoomId);
	return room.getLastMessages(index, range);
    }

    private ArrayList<ChatMessage> getAfterTimeStampEntries(String chatRoomId,
	    Date date) {
	ChatRoom room = chatRooms.get(chatRoomId);
	return room.getAfterTimeStampMessages(date);
    }
}
