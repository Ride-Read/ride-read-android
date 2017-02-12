package com.rideread.rideread;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.rideread.rideread.event.EmptyEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Jackbing on 2017/1/26.
 */

public class BaseActivity extends AppCompatActivity {


    public  AVIMClient client;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!App.baseQueue.contains(this)){
            App.baseQueue.add(this);
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        //EventBus.getDefault().register(this);
    }

    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void startActivity(Class<?> cls, String... objs) {
        Intent intent = new Intent(this, cls);
        for (int i = 0; i < objs.length; i++) {
            intent.putExtra(objs[i], objs[i]);
        }
        startActivity(intent);
    }

    protected boolean filterException(Exception e) {
        if (e != null) {
            e.printStackTrace();
            toast(e.getMessage());
            return false;
        } else {
            return true;
        }
    }

    protected void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //EventBus.getDefault().unregister(this);
    }



    public AVIMClient getClient() {
        return client;
    }

    public void setClient(AVIMClient client) {
        this.client = client;
    }
}

