package br.com.jackson.quickchat;

import java.util.Date;

public class Message implements Comparable <Message> {
    private String user;
    private Date date;
    private String text;


    @Override
    public int compareTo(Message other) {
        return date.compareTo(other.date);
    }

    public Message(String user, Date date, String text) {
        this.user = user;
        this.date = date;
        this.text = text;
    }

    public Message(){}

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
