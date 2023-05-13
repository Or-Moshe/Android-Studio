package com.example.obscalesraceapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.obscalesraceapp.Logic.GameManager;
import com.example.obscalesraceapp.Models.ScoreItem;
import com.example.obscalesraceapp.Models.UserInfo;
import com.example.obscalesraceapp.R;
import com.example.obscalesraceapp.Utilities.DataManager;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.gson.Gson;
import android.media.MediaPlayer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {

    private final int DELAY_GENERATING_OBSTACLES = 4000;
    private final int DELAY_UPDATING_MATRIX = 1000;
    private final String WARNING_MSG = "Be Careful Body!", FAILED_MSG = "Sorry Body, You Lose!";
    private String sensors_mode, level_mode, current_user_json;
    private final Handler handler_gen_obs = new Handler();
    private final Handler handler_upd_mat = new Handler();

    private GameManager gameManager;
    //private AppCompatImageView main_IMG_background;
    //private AppCompatImageView lose_IMG_background;
    private Button left_btn, right_btn;
    private TextView score_text;
    private int images[];

    private Set<Drawable> coinsSet;
    private Drawable player_drawable;
    private ShapeableImageView[] heartsArr;
    private Runnable runnable_gen_obs;
    private Runnable runnable_upd_mat;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.gameManager = new GameManager();
        this.player_drawable = getDrawable(R.drawable.toilet);

        String player_position = gameManager.getPlayer_position();
        String tag = gameManager.getImageTag(player_position);
        ImageView player_image = findImageByTag(tag);
        gameManager.setVisibility(player_image, player_drawable, ImageView.VISIBLE);

        setParamsFromAnotherIntent();
        findViews();
        buttonsLogic();
        runnableLogic();

        this.heartsArr = new ShapeableImageView[]{
                findViewById(R.id.heart3_id),
                findViewById(R.id.heart2_id),
                findViewById(R.id.heart1_id)
        };
        this.images = new int[]{
                R.drawable.poop_png/*,
                R.drawable.tampon*/
        };

        this.coinsSet = new HashSet<Drawable>();
        coinsSet.add(getDrawable(R.drawable.tampon));

    }

    @Override
    protected void onPause() {
        super.onPause();
        //this.runnable_gen_obs.wait();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //this.runnable_gen_obs.wait();
    }

    private void runnableLogic(){
        /*scheduler for generating new obstacles*/
        this.runnable_gen_obs = new Runnable() {
            @Override
            public void run() {
                try {
                    String position = gameManager.generateRandomObsPosition();
                    String tag = gameManager.getImageTag(position);
                    ImageView new_image_view = findImageByTag(tag);

                    int rand_int = gameManager.generateRandomIntInRange(images.length);
                    Drawable my_drawable = getDrawable(images[rand_int]);
                    gameManager.addImageToMap(new_image_view, position);
                    gameManager.setVisibility(new_image_view, my_drawable, ImageView.VISIBLE);

                    handler_gen_obs.postDelayed(this, DELAY_GENERATING_OBSTACLES);
                }catch (Exception ex){
                    Log.e("Exeption in runnable_gen_obs", "run: " + ex.getMessage() + ex.getCause());
                }
            }
        };

        /*scheduler for advancing obstacles on axis y*/
        this.runnable_upd_mat = new Runnable() {
            @Override
            public void run() {
                try {
                    advanceObs();
                    handler_upd_mat.postDelayed(this, DELAY_UPDATING_MATRIX);       //Do it again in a second
                }catch (Exception ex){
                    Log.d("Exeption in runnable_upd_mat", "run: " + ex.getMessage() + ex.getCause() + ex.getStackTrace());
                }
            }
        };
         //Do it again in a 3 seconds
        handler_upd_mat.postDelayed(runnable_upd_mat, DELAY_UPDATING_MATRIX);       //Do it again in a second
        handler_gen_obs.postDelayed(runnable_gen_obs, 100);
    }

    private void advanceObs(){
        Map<String, ImageView> positionToImageMap = gameManager.getPositionToImageMap();
        if(positionToImageMap.isEmpty()){
            return;
        }
        Iterator<String> iterator = positionToImageMap.keySet().iterator();
        while (iterator.hasNext()) {
            String old_position = iterator.next();
            ImageView old_image_view = positionToImageMap.get(old_position);
            Drawable old_drawable = old_image_view.getDrawable();
            int obs_row = Integer.parseInt(old_position.substring(0, 1));
            int obs_col = Integer.parseInt(old_position.substring(1, 2));
            if(gameManager.isLastRow(obs_row)){
                if(gameManager.isHit(old_position)){
                    if(gameManager.isCoin(old_drawable, coinsSet)){
                        //is coin
                        isCoinLogic();
                        positionToImageMap.remove(old_position);
                    }else{
                        hitLogic();
                        return;
                    }
                }else{
                    positionToImageMap.remove(old_position);
                    gameManager.setVisibility(old_image_view, old_drawable, ImageView.INVISIBLE);
                }
            }else {
                String new_position = (obs_row+1) * 10 + obs_col + "";
                String new_tag = gameManager.getImageTag(new_position);
                ImageView new_image_view = findImageByTag(new_tag);
                gameManager.addImageToMap(new_image_view, new_position);
                gameManager.replacePosition(old_position, new_position, old_drawable);
                gameManager.setVisibility(old_image_view, old_drawable, ImageView.INVISIBLE);
            }
        }
    }

    private void isCoinLogic(){
        doHittingSound(R.raw.toilet_flush, 1.0f,1.0f);
        String player_position = gameManager.getPlayer_position();
        String player_tag = gameManager.getImageTag(player_position);
        ImageView player_image_view = findImageByTag(player_tag);
        gameManager.setVisibility(player_image_view, player_drawable, ImageView.VISIBLE);

        score_text.setText(""+gameManager.addScore());
    }

    private void hitLogic(){
        doHittingSound(R.raw.toilet_flushingmp3, 1.0f,1.0f);
        vibrate();
        //killRunnables();
        removeHeart();
        gameManager.cleanGame();
        String msg = gameManager.getLife() == 0 ? FAILED_MSG : WARNING_MSG;
        if(gameManager.isGameEnded()){
            killRunnables();
            calculateScore();
            showToast(msg, Toast.LENGTH_LONG);
            showLoseImg();
            showScoresTable();
        }else{
            showToast(msg, Toast.LENGTH_LONG);
            Log.d("getPositionToImageMap size", "map size: " + gameManager.getPositionToImageMap().size());
            setStartPlayerPosition();
            //runnableLogic();
        }
    }

    private void setStartPlayerPosition(){
        String old_player_position = gameManager.getPlayer_position();
        String old_tag = gameManager.getImageTag(old_player_position);
        ImageView old_image_view = findImageByTag(old_tag);
        Drawable old_drawable = old_image_view.getDrawable();
        gameManager.setVisibility(old_image_view, old_drawable, ImageView.INVISIBLE);

        String new_player_position = gameManager.getPlayer_position();
        String new_tag = gameManager.getImageTag(new_player_position);
        ImageView new_image_view = findImageByTag(new_tag);
        gameManager.setVisibility(new_image_view, old_drawable, ImageView.VISIBLE);
    }

    private void doHittingSound(int rowId, float leftVolume, float rightVolume){
        mediaPlayer = MediaPlayer.create(this, rowId);
        mediaPlayer.setVolume(leftVolume, rightVolume);
        mediaPlayer.start();
    }
    private void vibrate(){
        // Vibrate for 500 milliseconds
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            vibrator.vibrate(500);
        }
    }
    private void removeHeart(){
        int life = gameManager.decreaseLife();
        if(life >= 0){
            gameManager.setVisibility(this.heartsArr[life], null, ShapeableImageView.INVISIBLE);
        }
    }

    private void showToast(String msg, int time){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void calculateScore(){
        int rank = 1;
        UserInfo current_user = new Gson().fromJson(current_user_json, UserInfo.class);
        ScoreItem scoreItem = new ScoreItem(current_user.getIcon(), current_user.getName(), gameManager.getScore(), rank);
        DataManager.getInstance().writeScoreToSP(scoreItem);
    }
    private void killRunnables(){
        handler_upd_mat.removeCallbacks(runnable_upd_mat);
        handler_gen_obs.removeCallbacks(runnable_gen_obs);
    }

    private void showLoseImg(){
        LinearLayout matrix_layout = (LinearLayout)findViewById(R.id.matrix_layout);
        //Glide.with(this).load(R.drawable.lose).fitCenter().into(viewTarget);
        matrix_layout.setBackgroundResource(R.drawable.lose);
    }

    private void showScoresTable(){
        Intent myIntent = new Intent(MainActivity.this, ScoreTableActivity.class);
        myIntent.putExtra("current_user_json", current_user_json); //Optional parameters
        startActivity(myIntent);
    }

    private ImageView findImageByTag(String tag) {
        LinearLayout matrix_layout = (LinearLayout)findViewById(R.id.matrix_layout);
        ImageView image_view = matrix_layout.findViewWithTag(tag);
        if(image_view == null){
            throw new RuntimeException("image not found" + " tag: " + tag);
        }
        return (ImageView)matrix_layout.findViewWithTag(tag);
    }

    private void findViews(){
        this.score_text = (TextView)findViewById(R.id.score);
    }

    private void buttonsLogic(){
        this.left_btn = findViewById(R.id.arrow_btn_left_id);
        this.right_btn = findViewById(R.id.arrow_btn_right_id);
        this.left_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String player_position = gameManager.getPlayer_position();
                String tag = gameManager.getImageTag(player_position);
                ImageView old_image_view = findImageByTag(tag);

                String new_player_position = gameManager.getNewPlayerPosition(old_image_view, player_drawable, "LEFT");
                String new_tag = gameManager.getImageTag(new_player_position);
                ImageView new_image_view = findImageByTag(new_tag);

                gameManager.replacePlayerPosition(old_image_view, new_image_view, player_drawable);
            }
        });

        this.right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String player_position = gameManager.getPlayer_position();
                String tag = gameManager.getImageTag(player_position);
                ImageView old_image_view = findImageByTag(tag);

                String new_player_position = gameManager.getNewPlayerPosition(old_image_view, player_drawable, "RIGHT");
                String new_tag = gameManager.getImageTag(new_player_position);
                ImageView new_image_view = findImageByTag(new_tag);

                gameManager.replacePlayerPosition(old_image_view, new_image_view, player_drawable);
            }
        });
    }

    private void setParamsFromAnotherIntent(){
        this.current_user_json = getIntent().getStringExtra("current_user_json");
    }
}