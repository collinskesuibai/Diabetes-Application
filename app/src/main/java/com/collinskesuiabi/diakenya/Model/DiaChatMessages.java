package com.collinskesuiabi.diakenya.Model;

public class DiaChatMessages {

    private String Message,Sender,Receiver,Date,Time;

    public DiaChatMessages() {
    }

    public DiaChatMessages(String message, String sender, String receiver, String date, String time) {
        Message = message;
        Sender = sender;
        Receiver = receiver;
        Date = date;
        Time = time;
    }



    public String getMessage() {
        return Message;
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
