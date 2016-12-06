package com.pette.server.common;

import java.io.Serializable;
import java.util.Date;

public class SendMessage implements Serializable {
    private String chatroomId;
    private String UUID;
    private String messageBody;
    private String senderName;
    private Date timeStamp;

    public SendMessage(String chatroomId, String UUID, String messageBody, String senderName, Date timeStamp) {
        this.chatroomId = chatroomId;
        this.UUID = UUID;
        this.messageBody = messageBody;
        this.senderName = senderName;
        this.timeStamp = timeStamp;
    }

    public String getChatroomId() {
        return chatroomId;
    }

    public String getUUID() {
        return UUID;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public String getSenderName() {
        return senderName;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }
}
