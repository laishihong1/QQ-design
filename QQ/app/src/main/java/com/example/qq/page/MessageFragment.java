package com.example.qq.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qq.Client.Message;
import com.example.qq.R;
import com.example.qq.ToolClass.MessageType;
import com.example.qq.ToolClass.MyAdapter;
import com.example.qq.ToolClass.SenderAcceptGenderMessage;
import com.example.qq.ToolClass.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MessageFragment extends Fragment {
    private static Count count;
    private final List<Message> list = new ArrayList<>();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        count = data.getCount();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.message_fragment, null);
        final ListView listView = view.findViewById(R.id.message_fragment_lv);

        for (String s : SenderAcceptGenderMessage.getMessages().keySet()) {
            list.add(SenderAcceptGenderMessage.getMessages().get(s));
        }

        if (!list.isEmpty()) {
            listView.setOnItemClickListener((parent, view1, position, id) -> {
                if (list.get(position).getMesType().equals(MessageType.MESSAGE_ADD_FRIEND)) {
                    Intent intent = new Intent(count, AddFriend.class);
                    intent.putExtra("message", list.get(position));
                    MessageFragment.this.startActivity(intent);
                }else if(list.get(position).getMesType().equals(MessageType.MESSAGE_COMM_MES)){
                    Intent intent=new Intent(count,ContentMessage.class);
                    intent.putExtra("message",list.get(position));
                    startActivity(intent);
                }
            });

            listView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
                menu.add(0,0,0,"删除");
                menu.add(0,1,0,"置顶");
            });

            MyAdapter myBaseApter = new MyAdapter(SenderAcceptGenderMessage.getMessages(), count);
            listView.setAdapter(myBaseApter);
        }

        return view;
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo= (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String id = String.valueOf(adapterContextMenuInfo.id);
         switch (item.getItemId()){
             case 0:
                 list.remove((int)adapterContextMenuInfo.id);
                 MyAdapter myAdapter=new MyAdapter(list,count);
                 myAdapter.notifyDataSetChanged();
                 break;
             case 1:
                    List<String>stringAccount=new ArrayList<>();
                    List<Message>messages=new ArrayList<>();
                    HashMap<String, Message> messages1 = SenderAcceptGenderMessage.getMessages();
                 for (String s : messages1.keySet()) {
                     stringAccount.add(s);
                     messages.add(messages1.get(s));
                 }

                 SenderAcceptGenderMessage.setMessages(stringAccount.get((int)adapterContextMenuInfo.id),messages.get((int)adapterContextMenuInfo.id));

                 stringAccount.remove((int)adapterContextMenuInfo.id);
                 messages.remove((int)adapterContextMenuInfo.id);
                 HashMap<String,Message>hashMap=new HashMap<>();
                 Iterator<String> iterator = messages1.keySet().iterator();
                 int i=0;
                 while (iterator.hasNext()){
                    hashMap.put(stringAccount.get(i),messages.get(i));
                    i++;
                 }

                 MyAdapter myAdapter1=new MyAdapter(hashMap,count);
                 myAdapter1.notifyDataSetChanged();
                 break;
             default:
                 break;
         }
        return super.onContextItemSelected(item);
    }
}


