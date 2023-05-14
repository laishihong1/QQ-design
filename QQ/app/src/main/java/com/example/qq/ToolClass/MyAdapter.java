package com.example.qq.ToolClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qq.Client.Message;
import com.example.qq.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyAdapter extends BaseAdapter {


    private  HashMap<String,Message>hashMap;
    private  Context context;
    private List<Message>list=new ArrayList<>();  //好友申请集合

    public MyAdapter(HashMap<String,Message> hashMap, Context context) {
        this.hashMap=hashMap;
        this.context = context;
        bianli();
    }

    public MyAdapter(List<Message>list,Context context){
        this.list=list;
        this.context=context;
    }

    private void bianli(){
           list.clear();
        for (String s : hashMap.keySet()) {
          if(hashMap.get(s).getMesType().equals(MessageType.MESSAGE_ADD_FRIEND)){
                list.add(hashMap.get(s));
          }
          else if(hashMap.get(s).getMesType().equals(MessageType.MESSAGE_ACCEPT_ADD_FRIEND)){
                list.add(hashMap.get(s));
          }
        }
    }


    @Override
    public int getCount() {
      return list.size();
    }

    @Override
    public Object getItem(int i) {
      return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        //视图转换器对象
        if(view==null) {
            //把布局文件转换成视图对象
           view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
            viewHolder= new ViewHolder();
            viewHolder.tvSender= view.findViewById(R.id.account);
            viewHolder.tvContent=  view.findViewById(R.id.account_message);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) view.getTag();
        }

        viewHolder.tvSender.setText(list.get(i).getGetter());
        viewHolder.tvContent.setText(list.get(i).getContent());

        return view;

    }

    static class ViewHolder{
        TextView tvSender,tvContent;
    }

}
