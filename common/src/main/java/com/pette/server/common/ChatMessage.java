package com.pette.server.common;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    public String body, sender, receiver, senderName;
    public String Date, Time;
    public String msgid;
    public boolean isMine;// Did I send the message.

    public ChatMessage(String Sender, String Receiver, String messageString,
	    String ID, boolean isMINE) {
	body = messageString;
	isMine = isMINE;
	sender = Sender;
	msgid = ID;
	receiver = Receiver;
	senderName = sender;
    }

    public void setMsgID() {

	msgid += 2;
    }
}
