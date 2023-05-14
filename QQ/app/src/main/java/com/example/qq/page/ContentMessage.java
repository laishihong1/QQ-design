package com.example.qq.page;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qq.Client.Message;
import com.example.qq.DataBase.Bean;
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

public class ContentMessage extends AppCompatActivity {
      private EditText editText;
      private Message messages;
      private ListView listView;

      private static final List<String>content=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_message);
        messages=(Message)getIntent().getSerializableExtra("getter");
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().removeStickyEvent(this);
    }

    private void init(){
        listView=findViewById(R.id.content_listview);
        listView.setDivider(null);
        editText=findViewById(R.id.content_message_edit);
        TextView getter_title = findViewById(R.id.getter_title);
        getter_title.setText(messages.getGetter());
        Button button = findViewById(R.id.send_message);

        button.setOnClickListener(v -> {
            Message message=new Message();
            message.setContent(editText.getText().toString());
            message.setMesType(MessageType.MESSAGE_COMM_MES);
            Bean bean = Count.getBean();
            message.setSender(bean.getAccount());
            message.setGetter(messages.getGetter());
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

    /**
     *         private Context context;   //上下文
     *         private String contentMessage; //发送或接收的内容
     *         private int flag;  // R.layout.sender_message_right 或 getter_message_left
     *         private int flags; // R.id.sender_message 或  R.id.getter_message
     */
//    private class ContentAdapter extends BaseAdapter {
//        private final List<String>list;
//        private final Context context;
//        private final int flag;
//
//
//        public ContentAdapter(Context context,List<String>list,int flag) {
//            this.context=context;
//            this.list=list;
//            this.flag=flag;
//        }
//
//        @Override
//        public int getCount() {
//            return list==null? 0:list.size();
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return list.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @SuppressLint("InflateParams")
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//      ViewHolder viewHolder = new ViewHolder();
//      if(convertView==null){
//         if(flag==R.layout.sender_message_right){
//             convertView=LayoutInflater.from(context).inflate(R.layout.sender_message_right,null);
//             viewHolder.textView=convertView.findViewById(R.id.sender_message);
//             convertView.setTag(viewHolder);
//         }
//         else if(flag==R.layout.getter_message_left){
//            convertView=LayoutInflater.from(context).inflate(R.layout.getter_message_left,null);
//             viewHolder=new ViewHolder();
//             viewHolder.textView=convertView.findViewById(R.id.getter_message);
//             convertView.setTag(viewHolder);
//         }
//      }
//      else {
//          viewHolder=(ViewHolder) convertView.getTag();
//      }
//          viewHolder.textView.setText(list.get(position));
//            return convertView;
//        }
//
//        private class ViewHolder{
//            TextView textView;
//        }
//
//    }

@Subscribe(threadMode = ThreadMode.MAIN)
public void chuli(Message message){
        if(message.getMesType().equals(MessageType.MESSAGE_COMM_MES)){
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





}
