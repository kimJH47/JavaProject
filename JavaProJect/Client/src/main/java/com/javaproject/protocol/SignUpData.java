package com.javaproject.protocol;



public class SignUpData implements Protocol{
    private static final long serialVersionUID = 1L;

    public static final short SIGNUP_REQUEST=10;
    public static final short SIGNUP_SUCCESS=20;
    public static final short SIGNUP_FAILED=30;

    private String ID;
    private String PW;
    private short protocol;
    private String name;
    public SignUpData(String ID,String PW, String name,short protocol){
        this.ID = ID;
        this.PW = PW;
        this.protocol =protocol;
        this.name =name;
    }
    public SignUpData(short protocol){
        this.protocol = protocol;
    }

    public String getID() {
        return ID;
    }

    public String getPW() {
        return PW;
    }

    @Override
    public short getProtocol() {
        return protocol;
    }

    @Override
    public String getName() {
        return null;
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
