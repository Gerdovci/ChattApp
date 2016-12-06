package com.pette.server.common;

import java.io.Serializable;

public class UpdateRequest implements Serializable {
    private String chatRoomId;

    public String getUsername() {
        return username;
    }

    private String username;

    public UpdateRequest(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }
}
