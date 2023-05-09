package com.example.obscalesraceapp.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.obscalesraceapp.Models.UserInfo;
import com.example.obscalesraceapp.R;
import com.example.obscalesraceapp.Utilities.DataManager;

public class LoginActivity extends AppCompatActivity {

    private int icons[];
    private TextView registerNewUser, user_name_text;
    private Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logic_activity);

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
        UserInfo current_user = new UserInfo(user_name_val, icons[0]);
        // need to ask tom
        /*
        App.setCurrent_user(current_user);
        DataManager.getInstance().addUser(current_user);*/
        Intent myIntent = new Intent(LoginActivity.this, MenuActivityActivity.class);
        //myIntent.putExtra("current_user", current_user); //Optional parameters

        startActivity(myIntent);
    }

    public void registerNewUser(){
        TextView registerNewUser = (TextView)findViewById(R.id.registerNewUser);
        registerNewUser.setVisibility(TextView.INVISIBLE);
    }

    private void enableLogicBtn(Boolean isEnabled){
        this.login_btn.setEnabled(isEnabled);
    }
}