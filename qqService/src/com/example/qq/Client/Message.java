package com.example.qq.Client;


import java.io.Serializable;
import java.util.Arrays;

//��ʾһ���û���Ϣ
//��ʾ�ͻ��˺ͷ�����ͨѶʱ��Ϣ����
public class Message implements Serializable{

    /**
     *  Sender ���ͷ�
     *  Getter ���շ�
     *  content ����[����/����]
     *  senderTime ����ʱ��[����/����]
     *  mesType ��Ϣ����
     */

    //��ǿ������
    private  static final long serialVersionUID=1L;

    private String Sender; //���ͷ�
    private String Getter; //���շ�

    private String Account_name;   //�ǳ�
    private String Account_email;  //����

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

    private String content; //��������

    private String sendTime; //����ʱ��

    private String mesType; //��Ϣ���� �������ڽӿڶ�����Ϣ����

    private byte[]fileBytes;
    private int fileLen=0;
    private String dest; //�ļ�����λ��
    private String src; //·��

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
