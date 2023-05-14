package com.example.qq.page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.qq.R;
import com.example.qq.ToolClass.data;

import java.util.ArrayList;
import java.util.List;

public class GroupMesFragment extends Fragment {
    private Count count;
    private  List<String> list=new ArrayList<>();
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        count= data.getCount();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.group_fragment,null);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final ListView listView=view.findViewById(R.id.group_fragment_lv);
        list.clear();
        list.add("群聊系统....");
        if(!list.isEmpty())
        {
            listView.setOnItemClickListener((parent, view1, position, id) -> {

                //加一个点击列表进入群聊
                Intent intent=new Intent(count,GroupMessage.class);
                startActivity(intent);

            });

            GroupAdapter groupAdapter=new GroupAdapter(count,list);
            listView.setAdapter(groupAdapter);
        }

        return view;
    }

 private class GroupAdapter extends BaseAdapter{
        Context context;
        List<String>list;

     public GroupAdapter(Context context, List<String> list) {
         this.context = context;
         this.list = list;
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
        ViewHolder viewHolder = new ViewHolder();
         if(view==null){
             view = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false);
             viewHolder.textView= view.findViewById(R.id.account);
             view.setTag(viewHolder);
         }
          else{
             viewHolder=(ViewHolder) view.getTag();
         }
         viewHolder.textView.setText(list.get(i));
          return view;
     }

     private class ViewHolder{
         TextView textView;
     }

 }


}
