package com.javaproject.protocol;

public class LoginData implements Protocol {
    private static final long serialVersionUID = 1L;
    public final static short LOGIN_REQUEST = 100;
    private String ID;
    private String passWord;
    private String name;
    private short protocol;

    public LoginData(String ID, String passWord, short protocol) {
        this.ID = ID;
        this.passWord = passWord;
        this.protocol = protocol;
    }

    public String getID() {
        return ID;
    }

    @Override
    public short getProtocol() {
        return protocol;
    }

    @Override
    public String getName() {
        return ID;
    }

    public String getPassWord() {
        return passWord;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public void setMessage(String message) {

    }

    @Override
    public void setName(String name) {

    }
}
