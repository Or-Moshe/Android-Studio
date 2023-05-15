package com.example.obscalesraceapp.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.obscalesraceapp.R;

public class MenuActivityActivity extends AppCompatActivity {

    private RadioGroup radioGroup_speed, radioGroup_sensor;
    private Button submit_btn;
    private String level_mode, sensors_mode;

    private String current_user_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        setParamsFromAnotherIntent();
        findViews();
        // on below line we are adding check change listener for our radio group.
        radioGroup_speed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                RadioButton radioButton = findViewById(checkedId);
                level_mode = (String)radioButton.getText();
                if(sensors_mode != null){
                    enableLogicBtn(true);
                }
            }
        });

        radioGroup_sensor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                RadioButton radioButton = findViewById(checkedId);
                sensors_mode = (String)radioButton.getText();
                if(level_mode != null){
                    enableLogicBtn(true);
                }
                // on below line we are displaying a toast message.
            }
        });
        this.submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                onSubmit();
            }
        });
    }

    public void onSubmit(){
        Intent myIntent = new Intent(MenuActivityActivity.this, MainActivity.class);
        myIntent.putExtra("sensors_mode", sensors_mode);
        myIntent.putExtra("level_mode", level_mode);
        myIntent.putExtra("current_user_json", current_user_json);
        startActivity(myIntent);
    }

    private void setParamsFromAnotherIntent(){
        this.current_user_json = getIntent().getStringExtra("current_user_json");
    }
    private void findViews(){
        this.radioGroup_speed = (RadioGroup) findViewById(R.id.radioGroup_speed);
        this.radioGroup_sensor = (RadioGroup) findViewById(R.id.radioGroup_sensor);
        this.submit_btn = (Button) findViewById(R.id.submit);
    }

    private void enableLogicBtn(Boolean isEnabled){
        this.submit_btn.setEnabled(isEnabled);
    }
}
