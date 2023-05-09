package com.example.obscalesraceapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.obscalesraceapp.Adapter.ScoreTableAdapter;
import com.example.obscalesraceapp.R;
import com.example.obscalesraceapp.Utilities.DataManager;

public class ScoreTableActivity extends AppCompatActivity {

    private RecyclerView main_LST_scores;
    private Button new_game_btn, new_user_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

        findViews();
        initViews();

        this.new_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                onNewGameClick();
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
        this.new_game_btn = (Button)findViewById(R.id.new_game_btn);
        this.new_user_btn = (Button)findViewById(R.id.new_user_btn);
    }

    private void initViews() {
        ScoreTableAdapter scoreTableAdapter = new ScoreTableAdapter(DataManager.getInstance().getScores());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.main_LST_scores.setAdapter(scoreTableAdapter);
        this.main_LST_scores.setLayoutManager(linearLayoutManager);
    }

    private void onNewGameClick(){
        Intent myIntent = new Intent(ScoreTableActivity.this, MainActivity.class);
        //myIntent.putExtra("current_user", current_user); //Optional parameters
        startActivity(myIntent);
    }

    private void onNewUserClick(){
        Intent myIntent = new Intent(ScoreTableActivity.this, LoginActivity.class);
        //myIntent.putExtra("current_user", current_user); //Optional parameters
        startActivity(myIntent);
    }
}