package com.example.obscalesraceapp.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import com.example.obscalesraceapp.Models.ScoreItem;
import com.example.obscalesraceapp.R;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DataManager {

    private static DataManager INSTANCE;
    private ArrayList<ScoreItem> scores;

    private SharedPreferences sharedPref;

    private DataManager(Context context) {
        this.sharedPref = context.getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);
        if(this.scores == null) {
            this.scores = new ArrayList<>();
        }
    }

    public static void init(Context context){
        if(INSTANCE == null){
            INSTANCE = new DataManager(context);
        }
    }

    public static DataManager getInstance() {
        return INSTANCE;
    }

    public ArrayList<ScoreItem> getScores() {
        return scores;
    }

    public void setScores(ArrayList<ScoreItem> scores) {
        this.scores = scores;
    }

    public void writeScoreToSP(ScoreItem scoreItem){
        this.scores.add(scoreItem);
        Collections.sort(this.scores);
        setRanks();
        String scores_json = new Gson().toJson(this.scores);
        Log.d("write JSON", scores_json);

        putString(String.valueOf(R.string.preference_file_key), scores_json);
    }

    public ArrayList<ScoreItem> readScoresFromSP(){
        String scores_json = getString(String.valueOf(R.string.preference_file_key), "");
        Log.d("read JSON", scores_json);
        Type listType = new TypeToken<ArrayList<ScoreItem>>(){}.getType();
        return new Gson().fromJson(scores_json, listType);
    }

    private void setRanks(){
        for (int i = 0; i < this.scores.size(); i++) {
            ScoreItem scoreItem = this.scores.get(i);
            scoreItem.setRank(i+1);
            if(i == 0 || i == 1){
                scoreItem.setIcon_res_img(R.drawable.crown_yellow);
            }
        }
    }
    private void putString(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private void putInt(String key, int value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    private String getString(String key, String value) {
        return sharedPref.getString(key, value);
    }

    private int getInt(String key, int value) {
        return sharedPref.getInt(key, value);
    }
}
