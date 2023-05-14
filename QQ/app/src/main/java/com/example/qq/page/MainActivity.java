package com.example.qq.page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qq.Client.Message;
import com.example.qq.DataBase.Bean;
import com.example.qq.DataBase.MySqlite;
import com.example.qq.QQThread.AccessCountThread;
import com.example.qq.QQThread.ClientConnectServerThead;
import com.example.qq.R;
import com.example.qq.ToolClass.MessageType;
import com.example.qq.ToolClass.data;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText account, password;
    Button count, register;
    CheckBox box;
    MySqlite mySqlite = null;
    File file = null;
    private static MainActivity mainActivity=null;
    private static boolean connection_state;
    final private static String IP="192.168.31.85";
    final private static int POST=9999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        init();
    }

    private void init() {
        account = findViewById(R.id.qq_et_number);
        password = findViewById(R.id.qq_et_password);
        file = new File("/data/data/com.example.qq/shared_prefs/data.xml");
        count = findViewById(R.id.count);
        count.setOnClickListener(v -> {

            //先验证账号是否账号密码是否正确

            if (account.getText().toString().equals("") || password.getText().toString().equals("")) {
                account.setText("");
                password.setText("");
                Toast.makeText(this, "请确认账号或密码是否输入", Toast.LENGTH_SHORT).show();
            } else {
                Bean bean = new Bean(account.getText().toString(), password.getText().toString());
                Message message = new Message();
                message.setMesType(MessageType.Count);
                new Thread(new JudgeSenderService(bean, message)).start();

            }

        });
        register = findViewById(R.id.register);
        register.setOnClickListener(v -> {
            data.setListViewActivity(this);
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        });

        box = findViewById(R.id.qq_cbk_qq_save);
        box.setOnClickListener(v -> {
            //记住账号，密码
            SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("account", account.getText().toString());
            editor.putString("password", password.getText().toString());
            editor.commit();
        });
    }


   public   static MainActivity GetMainActivity(){
      return mainActivity;
   }

    //将数据发送给服务器，服务器检验账号密码是否正确，并且返回结果
    public class JudgeSenderService implements Runnable {

        private Bean bean = null;
        private Message message = null;

        public JudgeSenderService(Bean bean, Message message) {
            this.bean = bean;
            this.message = message;
        }

        @Override
        public void run() {
            boolean b = true;
            while (b) {
                Socket socket = null;

                    try {
                        socket = new Socket(IP,POST);
                        connection_state=true;

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                        try {
                            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(bean);
                            oos.writeObject(message);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {

                            ObjectInputStream ois = null;
                            ois = new ObjectInputStream(socket.getInputStream());
                            message = (Message) ois.readObject();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    if (message.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {

                        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("account", account.getText().toString());
                        editor.putString("password", password.getText().toString());

                        editor.commit();

                        Bean.setSocket(socket); //Socket[address=/192.168.43.14,port=9999,localPort=60058]
                        Intent intent = new Intent(MainActivity.this, Count.class);
                        intent.putExtra("bean",bean);
                        startActivity(intent);

                        Looper.prepare();
                        Toast toast = Toast.makeText(MainActivity.this, message.getContent(), Toast.LENGTH_SHORT);
                        toast.show();
                        Looper.loop();

                        b = false;


                    }

                    else if (message.getMesType().equals(MessageType.MESSAGE_FAIL)) {
                        b = false;
                        Looper.prepare();
                        Toast toast = Toast.makeText(MainActivity.this, message.getContent(), Toast.LENGTH_SHORT);
                        toast.show();
                        Looper.loop();
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            connection_state=false;
                        }
                    }

                }

            }

        }

    private void pan() {
        if (file != null) {
            SharedPreferences userInfo = getSharedPreferences("data", MODE_PRIVATE);
            String account = userInfo.getString("account", null);
            String password = userInfo.getString("password", null);
            Bean bean = new Bean(account, password);
            Message message = new Message();
            message.setMesType(MessageType.Count);
            new Thread(new AccessCountThread(bean, message)).start();
        }
    }

    @Override
    protected void onDestroy() {

        try {
            Message message=new Message();
            message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
            ObjectOutputStream oos = new ObjectOutputStream(ClientConnectServerThead.getSocket().getOutputStream());
            oos.writeObject(message);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }


    }












