package com.pette.server.common;


import java.io.Serializable;
import java.util.List;

public class UpdateResponse implements Serializable {
    private List<SendMessage> messages;

    public UpdateResponse(List<SendMessage> messages) {
        this.messages = messages;
    }

    public List<SendMessage> getMessages() {
        return messages;
    }
}
