package com.pette.server.chattserver.chat;

import com.pette.server.common.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ChatRoom {
    ArrayList<String> users;
    ArrayList<ChatMessage> messages;
    ConcurrentHashMap<String, Integer> indexes;


    public ChatRoom(ArrayList<String> users) {
        this.users = users;
        indexes = new ConcurrentHashMap<>();
        for (String user : users) {
            indexes.put(user, 0);
        }
        messages = new ArrayList<>();

    }

    public ArrayList<ChatMessage> getMessagesForUserAndUpdateIndex(String user) {
        Integer index = indexes.get(user);
        if (index != null) {
            ArrayList<ChatMessage> subList = new ArrayList<>(messages.subList(index, messages.size()));
            indexes.put(user, messages.size());
            return subList;
        }
        return null;
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
