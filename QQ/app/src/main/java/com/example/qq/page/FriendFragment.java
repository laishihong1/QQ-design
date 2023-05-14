package com.example.qq.page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qq.Client.Message;
import com.example.qq.R;
import com.example.qq.ToolClass.MyAdapter;
import com.example.qq.ToolClass.SenderAcceptGenderMessage;
import com.example.qq.ToolClass.data;

import java.util.ArrayList;
import java.util.List;

public class FriendFragment extends Fragment {
   private  Count count;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        count= data.getCount();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.friend_fragment,null);
        final ListView listView=view.findViewById(R.id.friend_fragment_lv);

        List<Message> list=new ArrayList<>();
        for (String s : SenderAcceptGenderMessage.getAddMessage().keySet()) {
            list.add(SenderAcceptGenderMessage.getAddMessage().get(s));
        }

        if(!list.isEmpty())
        {
            listView.setOnItemClickListener((parent, view1, position, id) -> {
                //加一个点击列表进入聊天窗口
                Intent intent=new Intent(count,ContentMessage.class);
                intent.putExtra("getter",list.get(position));
                startActivity(intent);
            });
        }

        MyAdapter myBaseApter=new MyAdapter(SenderAcceptGenderMessage.getAddMessage(),count);
        listView.setAdapter(myBaseApter);

        return view;
    }


}
