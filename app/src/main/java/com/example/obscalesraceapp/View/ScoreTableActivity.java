package com.example.obscalesraceapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.obscalesraceapp.Adapter.ScoreTableAdapter;
import com.example.obscalesraceapp.R;
import com.example.obscalesraceapp.Utilities.DataManager;

public class ScoreTableActivity extends AppCompatActivity {

    private RecyclerView main_LST_scores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_table);

        findViews();
        initViews();
    }

    private void findViews() {
        this.main_LST_scores = findViewById(R.id.main_LST_scores);
    }

    private void initViews() {
        ScoreTableAdapter scoreTableAdapter = new ScoreTableAdapter(DataManager.getInstance().getScores());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        this.main_LST_scores.setAdapter(scoreTableAdapter);
        this.main_LST_scores.setLayoutManager(linearLayoutManager);
    }
}