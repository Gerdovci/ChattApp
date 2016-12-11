package com.pette.server.chattserver.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom {
    ArrayList<String> users;
    ArrayList<ChatMessage> messages;
    ConcurrentHashMap<String, Integer> indexes;

    public ChatRoom(ArrayList<String> users) {
        this.users = users;
        indexes = new ConcurrentHashMap<>();
        messages = new ArrayList<>();
        for (String user : users) {
            indexes.put(user, 0);
        }
    }

    public ArrayList<ChatMessage> getMessagesForUserAndUpdateIndex(String user) {
        Integer index = indexes.get(user);
        if (index != null && messages.size() > 0) {
            ArrayList<ChatMessage> subList = new ArrayList<>(messages.subList(index, messages.size() - 1));
            indexes.put(user, messages.size() - 1);
            return subList;
        }
        return new ArrayList<>();
    }

    public ArrayList<ChatMessage> getSinceUUID(String UUID) {
        int index = findIndex(UUID);
        return new ArrayList<>(messages.subList(index, messages.size() - 1));
    }

    private int findIndex(String UUID) {
        for (int i = messages.size() - 1; i >= 0; i--) {
            if (messages.get(i).getUUID().equals(UUID)) {
                return i;
            }
        }
        return messages.size() - 1;
    }

    public ArrayList<ChatMessage> getLastMessages(int index, int range) {
	ArrayList<ChatMessage> returnMessages = new ArrayList<>();
	if (index < 0) {
	    index = 0;
	}
	if (index > messages.size()) {
	    index = messages.size();
	}
	if (range > messages.size()) {
	    range = messages.size();
	}
	if (index + range >= messages.size()) {
	    range = messages.size();
	}
	for (int i = index + range; i > index; i--) {
	    returnMessages.add(messages.get(messages.size() - i));
	}
	return returnMessages;
    }

    public void addMessage(ChatMessage message) {
	messages.add(message);
    }

    public ArrayList<String> getUsers() {
	return users;
    }

    public void setUsers(ArrayList<String> users) {
	this.users = users;
    }

    public ArrayList<ChatMessage> getMessages() {
	return messages;
    }

    public void setMessages(ArrayList<ChatMessage> messages) {
	this.messages = messages;
    }
}
