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

    private SharedPreferences sharedPref;

    private ScoreItem[] scores;
    private final int scores_size = 10;

    private DataManager(Context context) {
        this.sharedPref = context.getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);
        if(scores == null ){
            scores = new ScoreItem[scores_size];
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

    public void writeScoreToSP(ScoreItem scoreItem){
        for (int i=0; i< scores.length; i++){
            if(scores[i] == null || scores[i].getScore() < scoreItem.getScore()) {
                scores[i] = scoreItem;
                break;
            }
        }
        String scores_json = new Gson().toJson(scores);
        Log.d("write JSON", scores_json);
        putString(String.valueOf(R.string.preference_file_key), scores_json);
    }

    public ScoreItem[] readScoresFromSP(){
        String scores_json = getString(String.valueOf(R.string.preference_file_key), "");
        Log.d("read JSON", scores_json);
        this.scores = new Gson().fromJson(scores_json, ScoreItem[].class);
        if(this.scores != null && this.scores.length == 0){
            Arrays.sort(this.scores);
        }
        return this.scores;
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

    public ScoreItem[] getScores() {
        return scores;
    }

    public void setScores(ScoreItem[] scores) {
        this.scores = scores;
    }
}
