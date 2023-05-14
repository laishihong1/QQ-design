package com.example.qq.QQThread;

import com.example.qq.QQThread.ClientConnectServerThead;

import java.net.Socket;
import java.util.HashMap;

/**
 * 该类实现客户端线程集合管理
 */

public class ManagerClimentConnectServerThread {
    //把多个线程放入到hashMap集合    key:id    value: 线程
    private static HashMap<String, ClientConnectServerThead> hm=new HashMap<>(); //接收消息线程

    public  static  void addClientConnectServerThread(String userId,ClientConnectServerThead clientConnectServerThead){
        hm.put(userId,clientConnectServerThead);
    }

    //通过userId, 得到对应线程
    public static ClientConnectServerThead clientConnectServerThead(String userId){
        return hm.get(userId);
    }



}
