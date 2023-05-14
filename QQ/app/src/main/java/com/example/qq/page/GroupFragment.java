package com.example.qq.page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.qq.R;
import com.example.qq.ToolClass.data;

import java.util.ArrayList;
import java.util.List;

public class GroupFragment extends FriendFragment{
    private  Count count;
    private List<String>list=new ArrayList<>();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        count = data.getCount();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.group_fragment,null);
         @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ListView listView=view.findViewById(R.id.group_fragment_lv);
          list.add("群聊系统");
          GroupAdapter groupAdapter=new GroupAdapter(count,list);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            //加一个点击列表进入聊天窗口
            Intent intent=new Intent(count,GroupMessage.class);
            startActivity(intent);
        });

           listView.setAdapter(groupAdapter);

       return view;
    }

     private class GroupAdapter extends BaseAdapter{
        private List<String>list;
        private Count count;
      private GroupAdapter(Count count,List<String>list){
          this.list=list;
          this.count=count;
      }

         @Override
         public int getCount() {
             return list.size();
         }

         @Override
         public Object getItem(int position) {
             return null;
         }

         @Override
         public long getItemId(int position) {
             return position;
         }

         @Override
         public View getView(int i, View view, ViewGroup viewGroup) {
             ViewHolder viewHolder;

             if (view == null) {
                 //把布局文件转换成视图对象
                  view= LayoutInflater.from(count).inflate(R.layout.list_item, viewGroup, false);
                 viewHolder = new ViewHolder();
                 viewHolder.account =view.findViewById(R.id.account);
                 viewHolder.introduces=view.findViewById(R.id.account_message);
                 view.setTag(viewHolder);
             } else {
                 viewHolder = (ViewHolder)view.getTag();
             }
             viewHolder.account.setText(list.get(i));
             viewHolder.introduces.setText("这是一个群聊........");
             return view;
         }

         class ViewHolder {
             TextView account,introduces;
             ImageView imageView;
         }
     }
}
