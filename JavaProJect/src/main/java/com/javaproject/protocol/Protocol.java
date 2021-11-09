package com.javaproject.protocol;


import java.io.Serializable;
public interface Protocol extends Serializable {
    public short getProtocol();
    public String getName();
    public String getMessage();
    public void setMessage(String message);
    public void setName(String name);
}
