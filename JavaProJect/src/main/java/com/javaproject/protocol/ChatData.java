package com.javaproject.protocol;

public class ChatData implements Protocol{
    private static final long serialVersionUID = 1L;

    public static final short SEND_TEXT=10;

    private String message;
    private String name;
    private int roomNum;
    private short protocol;

    public ChatData(int roomNum,String name,String message,short protocol) {
        this.roomNum=roomNum;
        this.name = name;
        this.message = message;
        this.protocol =protocol;

    }
    public int getRoomNum() {
        return roomNum;
    }
    @Override
    public String toString() {
        return String.format("%s: %s", name,message);
    }

    @Override
    public short getProtocol() {
        // TODO Auto-generated method stub
        return protocol;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return name;
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return message;
    }

    @Override
    public void setMessage(String message) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setName(String name) {
        // TODO Auto-generated method stub

    }


}
