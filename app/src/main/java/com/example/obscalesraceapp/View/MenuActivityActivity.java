package com.example.obscalesraceapp.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.obscalesraceapp.R;

public class MenuActivityActivity extends AppCompatActivity {

    private RadioGroup radioGroup_speed, radioGroup_sensor;
    private String level_mode, sensors_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);

        findViews();
        // on below line we are adding check change listener for our radio group.
        radioGroup_speed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                RadioButton radioButton = findViewById(checkedId);
                level_mode = (String)radioButton.getText();
            }
        });

        radioGroup_sensor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                RadioButton radioButton = findViewById(checkedId);
                sensors_mode = (String)radioButton.getText();
                // on below line we are displaying a toast message.
            }
        });
    }

    public void submit(){
        Intent myIntent = new Intent(MenuActivityActivity.this, MainActivity.class);
        myIntent.putExtra("sensors_mode", sensors_mode);
        myIntent.putExtra("level_mode", level_mode);
        startActivity(myIntent);
    }

    private void findViews(){
        this.radioGroup_speed = (RadioGroup) findViewById(R.id.radioGroup_speed);
        this.radioGroup_sensor = (RadioGroup) findViewById(R.id.radioGroup_sensor);
    }
}
