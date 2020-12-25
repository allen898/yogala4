package com.edvard.poseestimation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login2Activity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String userUID;
    private TextInputLayout lyt_email;
    private TextInputLayout lyt_password;
    private CheckBox checkBox_remember_password;
    private CheckBox checkBox_automatic_login;
    private Intent intent;
    private Bundle bundle;
    private SharedPreferences sharedPreferences;
    private boolean rem_isCheck;
    private boolean auto_isCheck;
    private DatabaseReference mDB;
    private String uid;
    private int count;
    private FirebaseAuth mAuth;
    private  String[][] course_input = new String[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        final EditText edt_email = (EditText)findViewById(R.id.edt_email);
        final EditText edt_password = (EditText)findViewById(R.id.edt_password);
        Button btn_login = (Button)findViewById(R.id.btn_login);
        checkBox_remember_password = (CheckBox)findViewById(R.id.checkBox_remember_password);
        checkBox_remember_password.setChecked(true);
        checkBox_automatic_login = (CheckBox)findViewById(R.id.checkBox_automatic_login);
        sharedPreferences = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

        rem_isCheck = checkBox_remember_password.isChecked();
        auto_isCheck = checkBox_automatic_login.isChecked();

        /*if(sharedPreferences.getBoolean("rem_isCheck", false)) {
            checkBox_remember_password.setChecked(true);
            edt_email.setText(sharedPreferences.getString("email", ""));
            edt_password.setText(sharedPreferences.getString("password", ""));
            Log.e("自動恢復儲存的帳號密碼", "自動恢復");
            if (sharedPreferences.getBoolean("auto_isCheck", false)) {
                checkBox_automatic_login.setChecked(true);
                Toast.makeText(LoginActivity.this, "已自動登入", Toast.LENGTH_SHORT).show();
                intent = new Intent();
                intent.setClass(LoginActivity.this, ToolbarActivity.class);
                startActivity(intent);
                finish();
            }
        }

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser(); //呼叫FirebaseAuth類別的getCurrentUser方法可取得目前登入的使用者物件user
                if (user != null){
                    Log.d("onAuthStateChanged", "登入：" + user.getUid()); //若取得user物件不是null，代表登入成功
                    userUID = user.getUid();
                } else {
                    Log.d("onAuthStateChanged", "已登出"); //若取得user物件是null，代表使用者登出
                }
            }
        };*/

        btn_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (edt_email.getText().toString().equals("judge@gmail.com") == false) {

                }
                if (edt_email.getText().toString().equals("judge@gmail.com") && edt_password.getText().toString().equals("judge")) {
                    FakeLogin(view);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //auth = FirebaseAuth.getInstance();
        //auth.addAuthStateListener(authStateListener); //呼叫addAuthStateListener方法加入傾聽器屬性，每次LoginActivity首次顯示或是從背景返回前景時都會自動開始傾聽事件
    }

    @Override
    protected void onStop() {
        super.onStop();
        //auth.removeAuthStateListener(authStateListener); //呼叫removeAuthStateListener方法移除傾聽事件，每次LoginActivity進入背景或結束時都會停止傾聽事件
    }


    public void Login(View v){
        final String email = ((EditText)findViewById(R.id.edt_email))
                .getText().toString();
        final String password = ((EditText)findViewById(R.id.edt_password))
                .getText().toString();
        lyt_email = (TextInputLayout)findViewById(R.id.lyt_email);
        lyt_password = (TextInputLayout)findViewById(R.id.lyt_password);
        if (TextUtils.isEmpty(email)){
            lyt_email.setError(getString(R.string.plz_input_email));
            return;
        }
        if (TextUtils.isEmpty(password)){
            lyt_password.setError(getString(R.string.plz_input_password));
            return;
        }


        Log.d("AUTH", email+"/"+password);
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login2Activity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login2Activity.this, R.string.login_succes, Toast.LENGTH_SHORT).show();
                    // 記住使用者名稱、密碼
                    if (checkBox_remember_password.isChecked()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USER_EMAIL", email);
                        editor.putString("PASSWORD", password);
                        editor.putBoolean("rem_isCheck", rem_isCheck);
                        editor.putBoolean("auto_isCheck", rem_isCheck);
                        editor.commit();
                    }
                    //
                    intent = new Intent();
                    /*bundle = new Bundle();
                    bundle.putSerializable("course", course_input);
                    intent.putExtras(bundle);*/
                    //intent.setClass(Login2Activity.this, ToolbarActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login2Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void FakeLogin(View v) {
        Toast.makeText(Login2Activity.this, "登入成功", Toast.LENGTH_SHORT).show();
        intent = new Intent();
        intent.setClass(Login2Activity.this, CourseListActivity.class);
        startActivity(intent);
        finish();
    }
}
