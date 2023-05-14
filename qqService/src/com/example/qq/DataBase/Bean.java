package com.example.qq.DataBase;

import java.io.Serializable;

/**
 *    name 账号
 *
 */

public class Bean implements Serializable {

    private String account, password;

    //增强兼容性
    private static final long serialVersionUID = 1L;


    public Bean(String account, String password) {
        this.account = account;
        this.password = password;

    }

    @Override
    public String toString() {
        return "Bean：{"+account+"account,password"+password+"}";
    }

    public Bean() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
