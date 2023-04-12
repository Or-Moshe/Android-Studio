package com.example.obscalesraceapp.Models;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.obscalesraceapp.Logic.GameManager;
import com.example.obscalesraceapp.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final int DELAY_GENERATING_OBSTACLES = 4000;
    private final int DELAY_UPDATING_MATRIX = 1000;
    private final String WARNING_MSG = "Be Careful Body!";
    private final String FAILED_MSG = "Sorry Body, You Lose!";
    private int images[] = {R.drawable.poop, R.drawable.toilet2};
    private final Handler handler_gen_obs = new Handler();
    private final Handler handler_upd_mat = new Handler();

    private GameManager gameManager;
    private AppCompatImageView main_IMG_background;
    private Button left_btn, right_btn;
    private ShapeableImageView[] heartsArr;
    private Runnable runnable_gen_obs;
    private Runnable runnable_upd_mat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.gameManager = new GameManager();

        buttonsLogic();
        runnableLogic();

        this.heartsArr = new ShapeableImageView[]{
                findViewById(R.id.heart3_id),
                findViewById(R.id.heart2_id),
                findViewById(R.id.heart1_id)
        };
    }

    private void runnableLogic(){
        /*scheduler for generating new obstacles*/
        this.runnable_gen_obs = new Runnable() {
            @Override
            public void run() {
                try {
                    String position = gameManager.generateRandomObsPosition(images.length);
                    String tag = gameManager.getImageTag(position);
                    ImageView new_image_view = findImageByTag(tag);

                    gameManager.addImageToMap(new_image_view, position);
                    Map<String, ImageView> m =  gameManager.getPositionToImageMap();
                    gameManager.setVisibility(new_image_view, ImageView.VISIBLE);

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
                    Log.d("Exeption in runnable_upd_mat", "run: " + ex.getMessage() + ex.getCause());
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
            int obs_row = Integer.parseInt(old_position.substring(0, 1));
            int obs_col = Integer.parseInt(old_position.substring(1, 2));

            if(gameManager.isLastRow(obs_row)){
                if(gameManager.isHit(old_position)){
                    String msg = gameManager.getLife() == 0 ? FAILED_MSG : WARNING_MSG;
                    vibrate();
                    //killRunnables();
                    removeHeart();
                    showToast(msg, Toast.LENGTH_LONG);
                    gameManager.cleanGame();
                    //runnableLogic();
                    if(gameManager.isGameEnded()){
                        killRunnables();
                        showToast(msg, Toast.LENGTH_LONG);
                        showLoseImg();
                        return;
                    }
                }
            }else {
                String new_position = (obs_row+1) * 10 + obs_col + "";
                String new_tag = gameManager.getImageTag(new_position);
                ImageView new_image_view = findImageByTag(new_tag);
                gameManager.addImageToMap(new_image_view, new_position);
                gameManager.replacePosition(old_position, new_position);
            }
            gameManager.setVisibility(old_image_view, ImageView.INVISIBLE);
        }
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
            this.heartsArr[life].setVisibility(ShapeableImageView.INVISIBLE);
        }

    }

    private void showToast(String msg, int time){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void killRunnables(){
        handler_upd_mat.removeCallbacks(runnable_upd_mat);
        handler_gen_obs.removeCallbacks(runnable_gen_obs);
    }

    private void showLoseImg(){
        /*Glide
                .with(this)
                .load(R.drawable.poop)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(main_IMG_background);

        LinearLayout matrix_layout = (LinearLayout)findViewById(R.id.matrix_layout);
        ImageView imageView = new ImageView(Context);
        imageView.setImageResource(R.drawable.image_name);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(layoutParams);

        linearLayout.addView(imageView);*/
    }
    private ImageView findImageByTag(String tag) {
        LinearLayout matrix_layout = (LinearLayout)findViewById(R.id.matrix_layout);
        ImageView image_view = matrix_layout.findViewWithTag(tag);
        if(image_view == null){
            throw new RuntimeException("image not found" + " tag: " + tag);
        }
        return (ImageView)matrix_layout.findViewWithTag(tag);
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

                String new_player_position = gameManager.getNewPlayerPosition(old_image_view, "LEFT");
                String new_tag = gameManager.getImageTag(new_player_position);
                ImageView new_image_view = findImageByTag(new_tag);

                //gameManager.addImageToMap(new_image_view, new_player_position);
                gameManager.replacePlayerPosition(old_image_view, new_image_view);
            }
        });

        this.right_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String player_position = gameManager.getPlayer_position();
                String tag = gameManager.getImageTag(player_position);
                ImageView old_image_view = findImageByTag(tag);

                String new_player_position = gameManager.getNewPlayerPosition(old_image_view, "RIGHT");
                String new_tag = gameManager.getImageTag(new_player_position);
                ImageView new_image_view = findImageByTag(new_tag);

                //gameManager.addImageToMap(new_image_view, new_player_position);
                gameManager.replacePlayerPosition(old_image_view, new_image_view);
            }
        });
    }
}