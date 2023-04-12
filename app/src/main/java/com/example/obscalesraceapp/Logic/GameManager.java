package com.example.obscalesraceapp.Logic;
import android.widget.ImageView;

import java.util.Map;
import java.util.Random;

import java.util.concurrent.ConcurrentHashMap;

public class GameManager {

    private int life;
    private final int NUM_OF_COLS = 3;
    private final int NUM_OF_ROWS = 7;
    private Map<String, ImageView> positionToImageMap;
    private String player_position;

    public GameManager() {
        this.positionToImageMap = new ConcurrentHashMap<>();
        this.player_position = (NUM_OF_ROWS - 1)*10 + NUM_OF_COLS / 2 + "";
        this.life = 3;
    }

    public String generateRandomObsPosition(int range){
        Random rand = new Random();
        return "0" + (rand.nextInt(range) % NUM_OF_COLS);
        //return getImageTag(position);
    }

    public boolean isLastRow(int obs_row){
        return obs_row == NUM_OF_ROWS - 2;
    }

    public Boolean isHit(String obs_position){
        int obs_col = Integer.parseInt(obs_position) % 10;
        int player_col = Integer.parseInt(player_position) % 10;
        return obs_col == player_col;
    }

    /*private ImageView addNewImage(ImageView imageView){

        if(imageView == null){
            Log.e("error in addNewImage", "addNewImage: findImageByTag didnt find" );
            return null;
        }
        setVisibility(imageView, ImageView.VISIBLE);
        this.positionToImageMap.put(position, imageView);
        return imageView;
    }*/

    public String getNewPlayerPosition(ImageView imageView, String dir){
        int col = Integer.parseInt(player_position.substring(1,2));
        int row = NUM_OF_ROWS - 1;
        setVisibility(imageView, ImageView.INVISIBLE);
        player_position = dir == "LEFT" ? getLeftPositionClicked(col, row) : getRightPositionClicked(col, row);
        //positionToImageMap.remove(player_position);
        return player_position;
        //String new_tag = "image_" + player_position + "_tag";

        //addNewImage(new_tag, player_position);
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

    public void replacePosition(String old_position, String new_position){
        ImageView old_image_view = positionToImageMap.get(old_position);
        ImageView new_image_view = positionToImageMap.get(new_position);
        setVisibility(old_image_view, ImageView.INVISIBLE);
        setVisibility(new_image_view, ImageView.VISIBLE);
        positionToImageMap.remove(old_position);
        positionToImageMap.put(new_position, new_image_view);
    }

    public void replacePlayerPosition(ImageView old_image_view, ImageView new_image_view){
        setVisibility(old_image_view, ImageView.INVISIBLE);
        setVisibility(new_image_view, ImageView.VISIBLE);
    }

    public void cleanGame(){
        for (String position : positionToImageMap.keySet()) {
            ImageView imgView = positionToImageMap.get(position);
            if(position != (NUM_OF_ROWS - 1) * 10 + NUM_OF_COLS - 1 + ""){
                setVisibility(imgView, ImageView.INVISIBLE);
            }
        }
        this.positionToImageMap = new ConcurrentHashMap<>();
    }

    public void setVisibility(ImageView imageView, int visibility){
        if(imageView == null){
            //do something
            return;
        }
        imageView.setVisibility(visibility);
    }

    public int getNUM_OF_ROWS() {
        return NUM_OF_ROWS;
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

    public String getPlayer_position(){
       return this.player_position;
    }
    public boolean isGameEnded() {
        return this.life == 0;
    }
}
