package com.example.obscalesraceapp.Logic;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class GameManager {

    private final int NUM_OF_COLS = 5;
    private final int NUM_OF_ROWS = 9;
    private Map<String, ImageView> positionToImageMap;
    private String player_position;
    private int life, score, level;

    public GameManager() {
        this.positionToImageMap = new ConcurrentHashMap<>();
        this.player_position = (NUM_OF_ROWS - 1)*10 + NUM_OF_COLS / 2 + "";
        this.life = 3;
        this.score = 0;
        this.level = 1;
    }

    public String generateRandomObsPosition(){
        Random rand = new Random();
        return "0" + rand.nextInt(NUM_OF_COLS);
    }

    public int generateRandomIntInRange(int range){
        Random rand = new Random();
        return rand.nextInt(range);
    }
    public boolean isLastRow(int obs_row){
        return obs_row == NUM_OF_ROWS - 1;
    }

    public Boolean isHit(String obs_position){
        int obs_col = Integer.parseInt(obs_position) % 10;
        int player_col = Integer.parseInt(player_position) % 10;
        return obs_col == player_col;
    }

    public Boolean isCoin(Drawable current_drawable, Set<Drawable> coinsSet){
        for (Drawable coin : coinsSet) {
            if(current_drawable.getConstantState().equals(coin.getConstantState())){
                return true;
            }
        }
        return false;
    }

    public String getNewPlayerPosition(ImageView imageView, Drawable myDrawable, String dir){
        int col = Integer.parseInt(player_position.substring(1,2));
        int row = NUM_OF_ROWS - 1;
        setVisibility(imageView, myDrawable, ImageView.INVISIBLE);
        player_position = dir == "LEFT" ? getLeftPositionClicked(col, row) : getRightPositionClicked(col, row);
        return player_position;
    }

    private String getLeftPositionClicked(int col, int row){
        return col == 0 ? (row * 10) + (NUM_OF_COLS -1) + "" : (row * 10) + (col - 1) + "";
    }

    private String getRightPositionClicked(int col, int row){
        return col == NUM_OF_COLS -1 ? (row * 10) + "" : (row * 10) + (col + 1) + "";
    }

    public void addImageToMap(ImageView imageView, String new_position){
        if(imageView == null){
            throw new RuntimeException("addNewImage: findImageByTag didnt find" );
        }
        this.positionToImageMap.put(new_position, imageView);
    }

    public Map<String, ImageView> getPositionToImageMap() {
        return positionToImageMap;
    }

    public void replacePosition(String old_position, String new_position, Drawable myDrawable){
        ImageView old_image_view = positionToImageMap.get(old_position);
        ImageView new_image_view = positionToImageMap.get(new_position);

        setVisibility(old_image_view, myDrawable, ImageView.INVISIBLE);
        setVisibility(new_image_view, myDrawable, ImageView.VISIBLE);

        positionToImageMap.remove(old_position);
        positionToImageMap.put(new_position, new_image_view);
    }

    public void replacePlayerPosition(ImageView old_image_view, ImageView new_image_view, Drawable myDrawable){
        setVisibility(old_image_view, myDrawable, ImageView.INVISIBLE);
        setVisibility(new_image_view, myDrawable, ImageView.VISIBLE);
    }

    public void cleanGame(){
        for (String position : positionToImageMap.keySet()) {
            ImageView imgView = positionToImageMap.get(position);
            if(position != (NUM_OF_ROWS - 1) * 10 + NUM_OF_COLS - 1 + ""){
                setVisibility(imgView, null, ImageView.INVISIBLE);
            }
        }
        this.positionToImageMap = new ConcurrentHashMap<>();
        this.player_position = (NUM_OF_ROWS - 1)*10 + NUM_OF_COLS / 2 + "";
    }

    public void setVisibility(ImageView imageView, Drawable myDrawable, int visibility){
        if(imageView == null){
            //do something
            return;
        }
        if(myDrawable != null){
            imageView.setImageDrawable(myDrawable);
        }
        imageView.setVisibility(visibility);
    }

    public String getImageTag(String position){
        return "image_" + position + "_tag";
    }

    public int decreaseLife(){
        return --life;
    }

    public int getLife() {
        return this.life;
    }

    public int getScore() {
        return this.score;
    }

    public int addScore(){
        this.score += (100 * this.level);
        return this.score;
    }
    public String getPlayer_position(){

        return this.player_position;
    }

    public boolean isGameEnded() {
        return this.life == 0;
    }
}
