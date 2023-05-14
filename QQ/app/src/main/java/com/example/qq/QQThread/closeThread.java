package com.example.qq.QQThread;

import com.example.qq.Client.Message;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class closeThread implements Runnable {

    private Socket socket;
    private Message message;

    public closeThread(Socket socket, Message message) {
        this.socket = socket;
        this.message = message;
    }

    @Override
    public void run() {

        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);

            socket.close();
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}