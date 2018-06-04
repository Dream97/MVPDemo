package com.example.deepin.myapplication.model;

import com.example.deepin.myapplication.listener.MainListener;

public class MainModel {
    public void getData(String s,MainListener mainListener){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mainListener.onSuccess("获取成功");
    }
}
