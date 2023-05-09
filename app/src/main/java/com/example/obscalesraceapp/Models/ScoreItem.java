package com.example.obscalesraceapp.Models;

public class ScoreItem {

    private int player_res_img;
    private String player_name;
    private int score;
    private int rank;

    public ScoreItem(int player_res_img, String player_name, int score, int rank) {
        this.player_res_img = player_res_img;
        this.player_name = player_name;
        this.score = score;
        this.rank = rank;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPlayer_res_img() {
        return player_res_img;
    }

    public void setPlayer_res_img(int player_res_img) {
        this.player_res_img = player_res_img;
    }

    @Override
    public String toString() {
        return "ScoreItem{" +
                "player_name='" + player_name + '\'' +
                ", score=" + score +
                ", rank=" + rank +
                '}';
    }
}
