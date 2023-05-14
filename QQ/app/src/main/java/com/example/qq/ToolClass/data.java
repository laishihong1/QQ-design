package com.example.qq.ToolClass;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.qq.page.Count;
import com.example.qq.page.MainActivity;

import java.io.Serializable;

public class data implements Serializable {

private  static MainActivity listViewActivity;
private  static Count count;

    public static MainActivity getListViewActivity() {
        return listViewActivity;
    }

    public static void setListViewActivity(MainActivity listViewActivity) {
        data.listViewActivity = listViewActivity;
    }

    public static Count getCount() {
        return count;
    }

    public static void setCount(Count count) {
        data.count = count;
    }
}
