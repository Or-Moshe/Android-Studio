package com.example.obscalesraceapp.Utilities;

import com.example.obscalesraceapp.Models.ScoreItem;
import com.example.obscalesraceapp.Models.UserInfo;

import java.util.ArrayList;

public class DataManager {

    private static DataManager INSTANCE;
    private ArrayList<ScoreItem> scores;
    private ArrayList<UserInfo> users;

    private DataManager() {

        this.scores = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public static DataManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new DataManager();
        }
        return INSTANCE;
    }

    public ArrayList<ScoreItem> getScores(){
        return scores;
    }

    public ArrayList<UserInfo> getUsers(){
        return users;
    }

    public void addScoreItem(ScoreItem scoreItem){
        this.scores.add(scoreItem);
    }

    public void addUser(UserInfo current_user){
        this.users.add(current_user);
    }
}
