package com.example.qq.page;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qq.Client.Message;
import com.example.qq.DataBase.MySqlite;
import com.example.qq.R;
import com.example.qq.QQThread.RegisterThread;
import com.example.qq.ToolClass.MessageType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements Serializable {
    EditText account,password;
    Button enter,cansel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        EventBus.getDefault().register(this);
        init();
    }

    private void init(){
            account=findViewById(R.id.account);
            password=findViewById(R.id.register);
            enter=findViewById(R.id.enter);
            cansel=findViewById(R.id.cansel);
    }

    @Override
    protected void onResume() {
        enter.setOnClickListener(v -> {
            if(isNumeric(account.getText().toString())&&
                    isLetterDigit(password.getText().toString())&&
                    length(account.getText().toString())&&
                    length(password.getText().toString()))
            {
                new Thread(new RegisterThread(account.getText().toString(),password.getText().toString())).start();

//                Toast.makeText(this,"已注册",Toast.LENGTH_SHORT).show();
                account.setText("");
                password.setText("");
            }else{
                Toast.makeText(this,"账号或密码输入有误，账号只能纯数字，密码只包含数字或字母",Toast.LENGTH_SHORT).show();
            }
        });

        cansel.setOnClickListener(v -> {
            Intent intent=new Intent(Register.this,MainActivity.class);
            startActivity(intent);
        });

        super.onResume();
    }

    public boolean isNumeric(String str) {
        //Pattern pattern = Pattern.compile("^-?[0-9]+"); //这个也行
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//这个也行
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isLetterDigit(String str) {
        String regex = "^[a-z0-9A-Z]+$";
        return str.matches(regex);
    }

    public static boolean length(String str){
       return  str.length()>=9||str.length()<=11 ? true: false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void chuli(Message message){
        if(message.getMesType().equals(MessageType.REGISTER_SUCCEED)){
         Toast.makeText(Register.this,message.getContent(),Toast.LENGTH_SHORT).show();
        }
       else if(message.getMesType().equals(MessageType.REGISTER_FAIL)){
            Toast.makeText(Register.this,message.getContent(),Toast.LENGTH_SHORT).show();
        }
    }

}
