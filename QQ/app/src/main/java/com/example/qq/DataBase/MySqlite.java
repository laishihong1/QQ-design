package com.example.qq.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.qq.ToolClass.MessageType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MySqlite extends SQLiteOpenHelper implements Serializable {
    public MySqlite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public MySqlite(Context context){
        super(context,"itcast.db",null,1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table txl(account varchar(20),password varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void add(Bean bean){
        SQLiteDatabase db = getWritableDatabase();
        String sql="insert into txl(account,password) values(?,?)";
        db.execSQL(sql,new String[]{bean.getAccount(),bean.getPassword()});
        db.close();
    }

   public void update(Bean bean){
        SQLiteDatabase db = getWritableDatabase();
        String sql="update  txl set password=? where account=?";
        db.execSQL(sql,new String[]{bean.getPassword(),bean.getAccount()});
        db.close();
    }

   public void del(String account){
        SQLiteDatabase db = getWritableDatabase();
        String sql="delete from  txl where account=?";
        db.execSQL(sql,new String[]{account});
        db.close();
    }

   public Bean getBean(String account){
        SQLiteDatabase db = getReadableDatabase();
        String sql="select * from txl where account=?";
        Cursor cursor = db.rawQuery(sql, new String[]{account});

        if (cursor.moveToNext()){
            String accounts = cursor.getString(0);
            String password = cursor.getString(1);
            Bean bean=new Bean(accounts,password);
            db.close();
            return  bean;
        }
        db.close();
        return null;
    }
   public List<Bean> getAll(){
        SQLiteDatabase db = getReadableDatabase();
        String sql="select * from txl";
        Cursor cursor = db.rawQuery(sql, null);
        List<Bean> list=new ArrayList<>();
        while (cursor.moveToNext()){
            String account = cursor.getString(0);
            String password = cursor.getString(1);
            Bean bean=new Bean(account,password);
            list.add(bean);
        }
        db.close();
        return list;
    }

   public Cursor getCursor(){
        SQLiteDatabase db = getReadableDatabase();
        String sql="select * from txl";
       return db.rawQuery(sql, null);

    }
}
