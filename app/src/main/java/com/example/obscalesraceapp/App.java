package com.example.obscalesraceapp;

import android.app.Application;

import com.example.obscalesraceapp.Models.ScoreItem;
import com.example.obscalesraceapp.Models.UserInfo;
import com.example.obscalesraceapp.Utilities.DataManager;

import java.util.ArrayList;

public class App extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        DataManager.init(this);
        ArrayList<ScoreItem> scores = DataManager.getInstance().readScoresFromSP();
        DataManager.getInstance().setScores(scores != null ? scores : new ArrayList<>());
    }
}
