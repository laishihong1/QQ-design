package com.example.qq.QQThread;

import com.example.qq.Client.Message;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class AddFriendThread implements Runnable{

    private  Message message;

    public AddFriendThread(Message message) {
        this.message = message;
    }

    @Override
    public void run() {

          Socket socket=ClientConnectServerThead.getSocket();

          try {
              ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
              oos.writeObject(message);

          } catch (Exception e) {
              e.printStackTrace();
          }

    }
}
