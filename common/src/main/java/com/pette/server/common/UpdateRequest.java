package com.pette.server.common;

import java.io.Serializable;

public class UpdateRequest implements Serializable {
    private String chatRoomId;
    private Integer index;

    private Integer range;

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

    public Integer getIndex() {
        return index;
    }

    public Integer getRange() {
        return range;
    }
}
