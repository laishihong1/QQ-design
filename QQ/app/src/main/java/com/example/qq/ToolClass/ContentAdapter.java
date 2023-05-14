package com.example.qq.ToolClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.qq.R;
import com.example.qq.page.ContentMessage;

import java.util.List;

public class ContentAdapter extends BaseAdapter {
    private final List<String> list;
    private final Context context;
    private final int flag;


    public ContentAdapter(Context context,List<String>list,int flag) {
        this.context=context;
        this.list=list;
        this.flag=flag;
    }

    @Override
    public int getCount() {
        return list==null? 0:list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        if(convertView==null){
            if(flag== R.layout.sender_message_right){
                convertView= LayoutInflater.from(context).inflate(R.layout.sender_message_right,null);
                viewHolder.textView=convertView.findViewById(R.id.sender_message);
                convertView.setTag(viewHolder);
            }
            else if(flag==R.layout.getter_message_left){
                convertView=LayoutInflater.from(context).inflate(R.layout.getter_message_left,null);
                viewHolder=new ViewHolder();
                viewHolder.textView=convertView.findViewById(R.id.getter_message);
                convertView.setTag(viewHolder);
            }
        }
        else {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list.get(position));
        return convertView;
    }
    private class ViewHolder{
        TextView textView;
    }
}
