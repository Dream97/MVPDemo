package com.example.deepin.myapplication.main.mvp;

import com.example.deepin.myapplication.base.BasePresenter;
import com.example.deepin.myapplication.listener.MainListener;
import com.example.deepin.myapplication.main.MainActivity;
import com.example.deepin.myapplication.model.MainModel;

public class MainPresenter extends BasePresenter<MainActivity> implements MainContract.Presenter,MainListener{

    private MainModel mainModel;
    public MainPresenter(MainActivity mainActivity){
        this.view = mainActivity;
        mainModel = new MainModel();
    }
    @Override
    public void getData() {
        mainModel.getData("结果ok啦",this);
    }

    @Override
    public void onSuccess(String s) {
        view.showResult(s);

    }

    @Override
    public void onfail() {

    }
}
