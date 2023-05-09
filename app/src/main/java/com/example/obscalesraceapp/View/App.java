package com.example.obscalesraceapp.View;

import android.app.Application;

import com.example.obscalesraceapp.Models.UserInfo;

public class App extends Application {

    private static UserInfo current_user;

    public static UserInfo getCurrent_user() {
        return current_user;
    }
    public static void setCurrent_user(UserInfo current_user) {

        current_user = current_user;
    }
}
