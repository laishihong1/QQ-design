package com.example.qq.Client;


import java.io.Serializable;
import java.util.Arrays;

//表示一个用户信息
//表示客户端和服务器通讯时消息对象
public class Message implements Serializable{

    /**
     *  Sender 发送方
     *  Getter 接收方
     *  content 内容[接收/发送]
     *  senderTime 发送时间[接收/发送]
     *  mesType 消息类型
     */

    //增强兼容性
    private  static final long serialVersionUID=1L;

    private String Sender; //发送方
    private String Getter; //接收方

    private String Account_name;   //昵称
    private String Account_email;  //邮箱

    public String getAccount_name() {
        return Account_name;
    }

    public void setAccount_name(String account_name) {
        Account_name = account_name;
    }

    public String getAccount_email() {
        return Account_email;
    }

    public void setAccount_email(String account_email) {
        Account_email = account_email;
    }

    private String content; //发送内容

    private String sendTime; //发送时间

    private String mesType; //消息类型 ，可以在接口定义消息类型

    private byte[]fileBytes;
    private int fileLen=0;
    private String dest; //文件传输位置
    private String src; //路径

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getMesType() {
        return mesType;
    }

    public void setMesType(String mesType) {
        this.mesType = mesType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getGetter() {
        return Getter;
    }

    public void setGetter(String getter) {
        Getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }


    @Override
    public String toString() {
        return "Message{" +
                "Sender='" + Sender + '\'' +
                ", Getter='" + Getter + '\'' +
                ", content='" + content + '\'' +
                ", sendTime='" + sendTime + '\'' +
                ", mesType='" + mesType + '\'' +
                ", fileBytes=" + Arrays.toString(fileBytes) +
                ", fileLen=" + fileLen +
                ", dest='" + dest + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}
