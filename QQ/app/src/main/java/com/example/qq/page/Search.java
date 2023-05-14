package com.example.qq.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qq.Client.Message;
import com.example.qq.DataBase.Bean;
import com.example.qq.QQThread.AddFriendThread;
import com.example.qq.R;
import com.example.qq.ToolClass.MessageType;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Search extends AppCompatActivity {
    private EditText editText;       //输入搜索账户
    private Button search;
    ListView listView;
    private  List<Bean> beanList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        init();
        EventBus.getDefault().register(this);
    }


    private void init() {

        editText = findViewById(R.id.Search_text);
        search = findViewById(R.id.Search_search_button);

    }

    @Override
    protected void onResume() {

        search.setOnClickListener(v -> {
            Bean bean=Count.getBean();
            if (isNumeric(editText.getText().toString()) && !bean.getAccount().equals(editText.getText().toString())) {
                Message message = new Message();

                //发送当前登录用户的账户信息
                message.setSender(bean.getAccount());

                //要寻找的好友
                message.setGetter(editText.getText().toString());

                message.setMesType(MessageType.MESSAGE_SEARCH);

                //标记，让服务器执行好友查找
                new Thread(new AddFriendThread(message)).start();
                editText.setText("");

            } else {

                Toast.makeText(Search.this, "你输入有误，请重新输入", Toast.LENGTH_SHORT).show();
                editText.setText("");
            }
        });

        super.onResume();
    }

    public boolean isNumeric(String str) {

        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");//这个也行
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void sync(Bean bean){
        beanList.clear();
        beanList.add(bean);
        disp(beanList);
    }

    private void disp(List<Bean> beanList) {
        listView = findViewById(R.id.search_listview);

        listView.setOnCreateContextMenuListener((menu, v, menuInfo) -> {
            menu.add(0, 0, 0, "申请添加");
            menu.add(0, 1, 0, "取消添加");

        });

        MyBaseApter myBaseApter = new MyBaseApter(beanList);
        listView.setAdapter(myBaseApter);

        Toast.makeText(Search.this, "已查询", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case 0:

                Message message = new Message();
                message.setMesType(MessageType.MESSAGE_ADD_FRIEND);
                Bean bean = Count.getBean();

                message.setSender(bean.getAccount());
                message.setGetter(beanList.get(0).getAccount());
                new Thread(new AddFriendThread(message)).start();

                break;
            case 1:
                break;
        }

        return super.onContextItemSelected(item);
    }

    private class MyBaseApter extends BaseAdapter {

        private List<Bean> List;

        public MyBaseApter(List<Bean> beanList) {
            this.List = beanList;
        }

        @Override
        public int getCount() {
            return List.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView == null) {
                //把布局文件转换成视图对象
                convertView = LayoutInflater.from(Search.this).inflate(R.layout.list_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.account = convertView.findViewById(R.id.account);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.account.setText(beanList.get(position).getAccount());
            return convertView;
        }

        class ViewHolder {
            TextView account, password;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}