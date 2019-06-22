package com.collinskesuiabi.diakenya.Model;

public class DataChatMessages {

    private String MessageId,UserPhone,Message,Sender,Receiver,Date,Time;

    public DataChatMessages() {
    }

    public DataChatMessages(String messageId, String userPhone, String message, String sender, String receiver, String date, String time) {
        MessageId = messageId;
        UserPhone = userPhone;
        Message = message;
        Sender = sender;
        Receiver = receiver;
        Date = date;
        Time = time;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getMessage() {
        return Message;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
