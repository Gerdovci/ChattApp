package com.pette.server.common;

import java.io.Serializable;
import java.util.Date;

public class UpdateRequest implements Serializable {
    private String chatRoomId;
    private String username;
    private Integer index;
    private Integer range;
    private Date isPuzzle;

    public UpdateRequest(String chatRoomId, String username, Integer index, Integer range, Date isPuzzle) {
        this.chatRoomId = chatRoomId;
        this.username = username;
        this.index = index;
        this.range = range;
        this.isPuzzle = isPuzzle;
    }

    public UpdateRequest(String chatRoomId, String username) {
        this.chatRoomId = chatRoomId;
        this.username = username;
    }

    public String getUsername() {
        return username;
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

    public Date getPuzzle() {
        return isPuzzle;
    }
}
