package com.example.qq.QQThread;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.qq.Client.Message;
import com.example.qq.DataBase.Bean;
import com.example.qq.ToolClass.MessageType;
import com.example.qq.page.Count;
import com.example.qq.page.MainActivity;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class AccessCountThread extends AppCompatActivity implements Runnable{
   private Bean bean;
   private Message message;

    public AccessCountThread(Bean bean, Message message) {
        this.bean = bean;
        this.message = message;
    }

    @Override
    public void run() {
        try {

            Socket socket = new Socket("192.168.43.14", 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeObject(bean);
            oos.writeObject(message);

            ObjectInputStream ois = null;
            ois = new ObjectInputStream(socket.getInputStream());
            message = (Message) ois.readObject();

            if(message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
                Bean.setSocket(socket);
                Intent intent = new Intent( MainActivity.GetMainActivity(), Count.class);
                intent.putExtra("bean", bean);
                startActivity(intent);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
