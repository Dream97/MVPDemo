package com.example.deepin.myapplication.main.mvp;

public interface MainContract {
    interface View {
        void showResult(String s);
        void showFail(String s);
    }
    interface Presenter{
        void getData();
    }
}
