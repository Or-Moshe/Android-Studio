package com.example.obscalesraceapp.Utilities;

import com.example.obscalesraceapp.Models.ScoreItem;

import java.util.ArrayList;

public class DataManager {

    private static DataManager INSTANCE;
    ArrayList<ScoreItem> scores;

    private DataManager() {
        this.scores = new ArrayList<>();
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

    public void addScoreItem(String player_name, int score, int rank){
        this.scores.add(new ScoreItem(player_name, score, rank));
    }
}
