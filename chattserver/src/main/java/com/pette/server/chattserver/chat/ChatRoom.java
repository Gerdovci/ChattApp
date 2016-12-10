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
	for (String user : users) {
	    indexes.put(user, 0);
	}
	messages = new ArrayList<>();
    }

    public ArrayList<ChatMessage> getMessagesForUserAndUpdateIndex(String user) {
	Integer index = indexes.get(user);
	if (index != null && messages.size() > 1) {
	    ArrayList<ChatMessage> subList = new ArrayList<>(messages.subList(
		    index, messages.size() - 1));
	    // indexes.put(user, messages.size());
	    return subList;
	} else {
	    return messages;
	}
    }

    public ArrayList<ChatMessage> getAfterTimeStampMessages(Date date) {
	ArrayList<ChatMessage> returnList = new ArrayList<>();
	for (ChatMessage message : messages) {
	    System.out.println("###" + message.getMessageBody() + " "
		    + message.getTimeStamp());
	    if (message.getTimeStamp().after(date)) {
		System.out.println("##" + date);
		System.out.println("###after " + message.getMessageBody() + " "
			+ message.getTimeStamp());
		returnList.add(message);
	    }
	}
	return returnList;
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
