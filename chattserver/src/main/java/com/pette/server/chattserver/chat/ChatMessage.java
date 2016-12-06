package com.pette.server.chattserver.chat;

import java.util.Date;

public class ChatMessage {
    private String UUID;
    private String senderName;
    private String messageBody;
    private Date timeStamp;

    public ChatMessage(String senderName, String messageBody, Date timeStamp, String UUID) {
        this.senderName = senderName;
        this.messageBody = messageBody;
        this.timeStamp = timeStamp;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }


    public String getUUID() {
        return UUID;
    }
}
