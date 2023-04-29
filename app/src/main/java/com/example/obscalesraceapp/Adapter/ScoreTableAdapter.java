package com.example.obscalesraceapp.Adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.obscalesraceapp.Models.ScoreItem;
import com.example.obscalesraceapp.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class ScoreTableAdapter extends RecyclerView.Adapter<ScoreTableAdapter.ScoreItemViewHolder> {

    private ArrayList<ScoreItem> scores;

    public ScoreTableAdapter(ArrayList<ScoreItem> scores) {
        this.scores = scores;
    }

    @NonNull
    @Override
    public ScoreItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);
        ScoreItemViewHolder scoreItemViewHolder = new ScoreItemViewHolder(view);
        return scoreItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreItemViewHolder holder, int position) {
        ScoreItem scoreItem = getItem(position);
        //holder.player_profile_img.
        holder.player_name_LBL.setText(scoreItem.getPlayer_name());
        holder.player_score_LBL.setText(scoreItem.getScore());
        holder.player_rank_LBL.setText(scoreItem.getRank());
    }

    @Override
    public int getItemCount() {
        return this.scores == null ? 0 : this.scores.size();
    }

    private ScoreItem getItem(int position){
        return this.scores == null ? null : this.scores.get(position);
    }

    public class ScoreItemViewHolder extends RecyclerView.ViewHolder {

        private ShapeableImageView player_profile_img;
        private MaterialTextView player_name_LBL;
        private MaterialTextView player_score_LBL;
        private MaterialTextView player_rank_LBL;

        public ScoreItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.player_profile_img = itemView.findViewById(R.id.player_profile_img);
            this.player_name_LBL = itemView.findViewById(R.id.player_profile_img);
            this.player_score_LBL = itemView.findViewById(R.id.player_name_LBL);
            this.player_rank_LBL = itemView.findViewById(R.id.player_rank_LBL);
        }
    }
}