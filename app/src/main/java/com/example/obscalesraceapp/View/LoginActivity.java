package com.example.obscalesraceapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.obscalesraceapp.Models.UserInfo;
import com.example.obscalesraceapp.R;
import com.example.obscalesraceapp.Utilities.DataManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {

    private int icons[];
    private TextView registerNewUser, user_name_text;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logic_activity);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            return;

        }

        findViews();
        this.icons = new int[]{
            R.drawable.wolf_eyeglasses
        };

        this.user_name_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                enableLogicBtn(true);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        this.registerNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                registerNewUser();
            }
        });

        this.login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                onLoginClick();
            }
        });
    }


    private void findViews(){
        this.user_name_text = (TextView)findViewById(R.id.user_name);
        this.registerNewUser = (TextView)findViewById(R.id.registerNewUser);
        this.login_btn = (Button) findViewById(R.id.login_btn);
    }
    public void onLoginClick(){
        EditText user_name = (EditText)findViewById(R.id.user_name);
        String user_name_val = user_name.getText().toString();
        if(TextUtils.isEmpty(user_name_val)){
            //do something
            return;
        }
        String current_user_json = new Gson().toJson(new UserInfo(user_name_val, icons[0], getCurrentLocation()));
        Intent myIntent = new Intent(LoginActivity.this, MenuActivityActivity.class);
        myIntent.putExtra("current_user_json", current_user_json);
        startActivity(myIntent);
    }

    public void registerNewUser(){
        TextView registerNewUser = (TextView)findViewById(R.id.registerNewUser);
        registerNewUser.setVisibility(TextView.INVISIBLE);
    }

    private LatLng getCurrentLocation(){
        return new LatLng(1.955100735125836, 34.803954184130575);
    }

    private void enableLogicBtn(Boolean isEnabled){
        this.login_btn.setEnabled(isEnabled);
    }
}