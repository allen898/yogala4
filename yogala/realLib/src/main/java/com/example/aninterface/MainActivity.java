package com.example.aninterface;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Button btn_to_login = (Button)findViewById(R.id.btn_to_login);
        Button btn_to_register = (Button)findViewById(R.id.btn_to_register);
        btn_to_login.setOnClickListener(this);
        btn_to_register.setOnClickListener(this);
    }

    public void ToLogin(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    public void ToRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_to_login) {
            ToLogin(v);
        } else if (id == R.id.btn_to_register) {
            ToRegister(v);
        }
    }
}