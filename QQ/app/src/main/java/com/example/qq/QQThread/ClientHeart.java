package com.example.qq.QQThread;

import org.json.JSONObject;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class ClientHeart implements Runnable{
    private Socket socket;
    private ObjectOutputStream oos;
    public ClientHeart(Socket socket,ObjectOutputStream oos) {
        this.socket = socket;
        this.oos=oos;
    }

    @Override
    public void run() {
        try {

             while (true){
                 Thread.sleep(5*1000);
                 if(socket.isConnected()){
                     JSONObject json = new JSONObject();
                     json.put("time", LocalDateTime.now());
                     oos.writeObject(json);
                     oos.flush();
                 }
                  else{
                      socket.close();
                      System.exit(0);
                 }
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
