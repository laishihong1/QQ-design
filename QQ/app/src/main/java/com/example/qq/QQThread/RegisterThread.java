package com.example.qq.QQThread;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.example.qq.Client.Message;
import com.example.qq.DataBase.Bean;
import com.example.qq.ToolClass.MessageType;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

public class RegisterThread implements Runnable, Serializable {
    final private static String IP="192.168.31.85";
    final private static int POST=9999;
    private String account,password;
    public RegisterThread(String account,String password){
        this.account=account;
        this.password=password;

    }

    @Override
    public void run() {
            try {

                Socket socket=new Socket(IP,POST);
                Log.d(TAG,InetAddress.getLocalHost().toString());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                oos.writeObject(new Bean(this.account,this.password));

                Message message=new Message();
                message.setMesType(MessageType.REGISTER);
                oos.writeObject(message);

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message1=(Message)ois.readObject();
                EventBus.getDefault().post(message1);

                oos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
