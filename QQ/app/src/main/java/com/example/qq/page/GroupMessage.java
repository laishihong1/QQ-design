package com.example.qq.page;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qq.Client.Message;
import com.example.qq.QQThread.ClientConnectServerThead;
import com.example.qq.QQThread.SendMessageThread;
import com.example.qq.R;
import com.example.qq.ToolClass.ContentAdapter;
import com.example.qq.ToolClass.MessageType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class GroupMessage extends AppCompatActivity {
    private EditText editText;
    private ListView listView;
    private static final List<String> content=new ArrayList<>();
    private Button send_message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_message);
        init();
        EventBus.getDefault().register(this);
    }

    private void init(){
        listView=findViewById(R.id.content_listview);
        listView.setDivider(null);
        editText=findViewById(R.id.content_message_edit);
        TextView getter_title = findViewById(R.id.getter_title);
        getter_title.setText("群聊系统");

        send_message=findViewById(R.id.send_message);
        send_message.setOnClickListener(view -> {
            Message message = new Message();
            message.setContent(editText.getText().toString());
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);

            new Thread(new SendMessageThread(ClientConnectServerThead.getSocket(),message)).start();
            content.add(editText.getText().toString());
            ContentAdapter contentAdapter=new ContentAdapter(this,content,R.layout.sender_message_right);
            listView.setAdapter(contentAdapter);


            if(contentAdapter!=null){

                contentAdapter.notifyDataSetChanged();
                listView.setSelection(content.size()-1);

            }else{
                contentAdapter=new ContentAdapter(this,content,R.layout.sender_message_right);
                listView.setAdapter(contentAdapter);
                listView.setSelection(content.size()-1);
            }
            editText.setText("");
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void GroupSendMessage(Message message){
        if(message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)){
            content.add(message.getContent());
            ContentAdapter contentAdapter=new ContentAdapter(this,content,R.layout.getter_message_left);
            listView.setAdapter(contentAdapter);

            if(contentAdapter!=null){
                listView.setSelection(content.size());
                contentAdapter.notifyDataSetChanged();
            }else{
                contentAdapter=new ContentAdapter(this,content,R.layout.sender_message_right);
                listView.setAdapter(contentAdapter);
                contentAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
