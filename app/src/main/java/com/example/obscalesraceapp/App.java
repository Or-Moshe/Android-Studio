package com.example.obscalesraceapp;

import android.app.Application;

import com.example.obscalesraceapp.Models.UserInfo;
import com.example.obscalesraceapp.Utilities.DataManager;

public class App extends Application {

    private static UserInfo current_user;
    @Override
    public void onCreate(){
        super.onCreate();

        DataManager.init(this);
        DataManager.getInstance().setScores(DataManager.getInstance().readScoresFromSP());
    }

    public static UserInfo getCurrent_user() {
        return current_user;
    }
    public static void setCurrent_user(UserInfo current_user) {

        current_user = current_user;
    }
}
