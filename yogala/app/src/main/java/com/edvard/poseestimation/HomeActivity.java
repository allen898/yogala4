package com.edvard.poseestimation;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button btn_to_login = (Button)findViewById(com.example.aninterface.R.id.btn_to_login);
        Button btn_to_register = (Button)findViewById(com.example.aninterface.R.id.btn_to_register);
        btn_to_login.setOnClickListener(this);
        btn_to_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == com.example.aninterface.R.id.btn_to_login) {
            ToLogin(v);
        } else if (id == com.example.aninterface.R.id.btn_to_register) {
            ToRegister(v);
        }
    }

    public void ToLogin(View view){
        Intent intent = new Intent(this, Login2Activity.class);
        startActivity(intent);
    }
    public void ToRegister(View view){
        //Intent intent = new Intent(this, RegisterActivity.class);
        //startActivity(intent);
    }
}