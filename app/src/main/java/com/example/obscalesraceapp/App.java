package com.example.obscalesraceapp;

import android.app.Application;

import com.example.obscalesraceapp.Models.UserInfo;
import com.example.obscalesraceapp.Utilities.DataManager;

public class App extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        DataManager.init(this);
        DataManager.getInstance().setScores(DataManager.getInstance().readScoresFromSP());
    }
}
