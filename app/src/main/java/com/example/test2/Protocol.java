package com.example.test2;

import java.io.Serializable;

/**
 * Created by kngki on 2017-05-22.
 */

public class Protocol implements Serializable {
    public static final long serialVersionUID  = 1;

    public Protocol(String nickName){
        this.nickName = nickName;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String nickName;
    public String message;

}