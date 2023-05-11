package com.example.obscalesraceapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.obscalesraceapp.Adapter.ScoreTableAdapter;
import com.example.obscalesraceapp.Models.ScoreItem;
import com.example.obscalesraceapp.R;
import com.example.obscalesraceapp.Utilities.DataManager;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ScoreTableActivity extends AppCompatActivity {

    private RecyclerView main_LST_scores;
    private Button backToMenuPageClicked, new_user_btn;
    private String current_user_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

        setParamsFromAnotherIntent();
        findViews();
        initViews();

        this.backToMenuPageClicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                onBackToMenuPageClicked();
            }
        });

        this.new_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                onNewUserClick();
            }
        });
    }

    private void findViews() {
        this.main_LST_scores = (RecyclerView)findViewById(R.id.main_LST_scores);
        this.backToMenuPageClicked = (Button)findViewById(R.id.new_game_btn);
        this.new_user_btn = (Button)findViewById(R.id.new_user_btn);
    }

    private void initViews() {
        ArrayList<ScoreItem> records = (ArrayList<ScoreItem>)DataManager.getInstance().getScores().stream().limit(10).collect(Collectors.toList());
        ScoreTableAdapter scoreTableAdapter = new ScoreTableAdapter(records);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.main_LST_scores.setAdapter(scoreTableAdapter);
        this.main_LST_scores.setLayoutManager(linearLayoutManager);
    }

    private void onBackToMenuPageClicked(){
        Intent myIntent = new Intent(ScoreTableActivity.this, MenuActivityActivity.class);
        myIntent.putExtra("current_user_json", current_user_json); //Optional parameters
        startActivity(myIntent);
    }

    private void onNewUserClick(){
        Intent myIntent = new Intent(ScoreTableActivity.this, LoginActivity.class);
        //myIntent.putExtra("current_user", current_user); //Optional parameters
        startActivity(myIntent);
    }

    private void setParamsFromAnotherIntent(){
        this.current_user_json = getIntent().getStringExtra("current_user_json");
    }
}