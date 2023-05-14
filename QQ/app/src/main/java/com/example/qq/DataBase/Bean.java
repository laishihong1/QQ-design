package com.example.qq.DataBase;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.net.Socket;

/**
 *    name 账号
 *
 */

public class Bean implements Serializable {
  private  String account,password;
  private static Socket socket;

  private static Bean bean; //存入返回的好友查询数据
    //增强兼容性
    private  static final long serialVersionUID=1L;

    public Bean(String account, String password) {
        this.account = account;
        this.password = password;

    }


    public static Socket getSocket() {
        return socket; //Socket[address=/192.168.43.14,port=9999,localPort=37814]
    }

    public static void setSocket(Socket socket) {
        Bean.socket = socket; //Socket[address=/192.168.43.14,port=9999,localPort=37814]
    }

    public Bean() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public  String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Bean getBean() {
        return bean;
    }

    public static void setBean(Bean bean) {
        Bean.bean = bean;
    }

}
