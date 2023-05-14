package com.example.qq.page;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.qq.DataBase.Bean;
import com.example.qq.QQThread.ClientConnectServerThead;
import com.example.qq.R;
import com.example.qq.ToolClass.data;
import com.google.android.material.navigation.NavigationView;

public class Count extends AppCompatActivity {
    private static Bean bean;
    private Button countAccountText,search;
    private Button message,friend,group;
    private MessageFragment messageFragment;
    private FriendFragment friendFragment;
    private GroupFragment groupFragment;
    private DrawerLayout myDrawLayout;
    private ImageButton imageButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acount_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        bean =(Bean)getIntent().getSerializableExtra("bean");

        new Thread(new ClientConnectServerThead(Bean.getSocket())).start();

        data.setCount(this);

         init();
    }

       private void init(){
           message= findViewById(R.id.count_message);
           friend= findViewById(R.id.count_friend);
           group= findViewById(R.id.count_group);

            myDrawLayout=findViewById(R.id.drower_layout);
            NavigationView navigationView=(NavigationView) findViewById(R.id.nav_view);
            navigationView.setCheckedItem(R.id.nav_call);

           //当用户成功登录后，将用户账户输出
           countAccountText = findViewById(R.id.count_account);
           countAccountText.setText(bean.getAccount());

           imageButton=findViewById(R.id.count_imagebuttn);
           imageButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   myDrawLayout.openDrawer(GravityCompat.START);
                   navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                       @Override
                       public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                           switch (item.getItemId()){

                           }
                           myDrawLayout.closeDrawers();
                           return true;
                       }
                   });
               }
           });

           messageFragment=new MessageFragment();
           friendFragment=new FriendFragment();
           groupFragment=new GroupFragment();

           message.setOnClickListener(l);
           friend.setOnClickListener(l);
           group.setOnClickListener(l);

           search = findViewById(R.id.count_search);
           search.setOnClickListener(v -> {
               Intent intent = new Intent(Count.this, Search.class);
               startActivity(intent);
           });
       }
       

    View.OnClickListener l=new View.OnClickListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.count_message:
                    if(friendFragment!=null){
                        getSupportFragmentManager().beginTransaction().hide(friendFragment);
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,messageFragment).commitNowAllowingStateLoss();

                    break;
                case R.id.count_friend:
                    if(messageFragment!=null){
                        getSupportFragmentManager().beginTransaction().hide(messageFragment);
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment,friendFragment).commitNowAllowingStateLoss();
                    break;
                case R.id.count_group:
                      getSupportFragmentManager().beginTransaction().replace(R.id.fragment,groupFragment).commitNowAllowingStateLoss();
                default:
                    break;
            }

        }
    };

    public static Bean getBean() {
        return bean;
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}
