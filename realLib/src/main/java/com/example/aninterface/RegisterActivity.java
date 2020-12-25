package com.example.aninterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDB;
    private TextInputLayout lyt_firstname;
    private TextInputLayout lyt_lastname;
    private TextInputLayout lyt_email;
    private TextInputLayout lyt_password;
    private EditText edt_firstname;
    private EditText edt_lastname;
    private EditText edt_email;
    private EditText edt_password;
    private CheckBox checkBox_read;
    private boolean checkBox_read_isChecked;
    private Button btn_register;
    private FirebaseUser user;
    private Member member;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Register();
    }

    public void Register(){
        mAuth = FirebaseAuth.getInstance();
        mDB = FirebaseDatabase.getInstance().getReference();
        member = new Member();
        lyt_firstname = (TextInputLayout)findViewById(R.id.lyt_firstname);
        lyt_lastname = (TextInputLayout)findViewById(R.id.lyt_lastname);
        lyt_email = (TextInputLayout)findViewById(R.id.lyt_email);
        lyt_password = (TextInputLayout)findViewById(R.id.lyt_password);
        edt_firstname = (EditText)findViewById(R.id.edt_firstname);
        edt_lastname = (EditText)findViewById(R.id.edt_lastname);
        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_password = (EditText)findViewById(R.id.edt_password);
        checkBox_read = (CheckBox)findViewById(R.id.checkBox_read);
        checkBox_read.setChecked(false);
        checkBox_read_isChecked = false;
        checkBox_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox_read.isChecked()) {
                    checkBox_read_isChecked = true;
                    btn_register.setEnabled(true);
                } else {
                    checkBox_read_isChecked = false;
                    btn_register.setEnabled(false);
                }
            }
        });
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_register.setEnabled(false);
        btn_register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String firstname = edt_firstname.getText().toString();
                final String lastname = edt_lastname.getText().toString();
                final String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();
                if(TextUtils.isEmpty(firstname)){
                    lyt_firstname.setError(getString(R.string.plz_input_firstname));
                    return;
                }
                if(TextUtils.isEmpty(lastname)){
                    lyt_lastname.setError(getString(R.string.plz_input_lastname));
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    lyt_email.setError(getString(R.string.plz_input_email));
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    lyt_password.setError(getString(R.string.plz_input_password));
                    return;
                }
                lyt_firstname.setError("");
                lyt_lastname.setError("");
                lyt_email.setError("");
                lyt_password.setError("");
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() && checkBox_read.isChecked()){
                            InputUserInfo(firstname, lastname, email);
                            InputCourseData();
                            Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.setClass(RegisterActivity.this, ToolbarActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void InputUserInfo(String firstname, String lastname, String email){
        member = new Member(firstname, lastname, email);
        //可以用回傳值的方式寫，有需要的話再說
        /*mDB.child("members").child(email).setValue(member).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        })*/
        uid = mAuth.getInstance().getCurrentUser().getUid();
        mDB.child("members").child(uid).setValue(member);
    }
    public void InputCourseData() {
        // 現在只有三種課程，之後課程變成多少陣列大小就要改變
        Course[] course_list = new Course[9];
        course_list[0] = new Course(0, "睡前舒壓 1", "就是睡前舒壓 1", "睡前舒壓", false, false);
        course_list[1] = new Course(1, "睡前舒壓 2", "就是睡前舒壓 2", "睡前舒壓", false, false);
        course_list[2] = new Course(2, "睡前舒壓 3", "就是睡前舒壓 3", "睡前舒壓", false, false);
        course_list[3] = new Course(3, "早晨甦醒 1", "就是早晨甦醒 1", "早晨甦醒", false, false);
        course_list[4] = new Course(4, "早晨甦醒 2", "就是早晨甦醒 2", "早晨甦醒", false, false);
        course_list[5] = new Course(5, "早晨甦醒 3", "就是早晨甦醒 3", "早晨甦醒", false, false);
        course_list[6] = new Course(6, "健身美體 1", "就是健身美體 1", "健身美體", false, false);
        course_list[7] = new Course(7, "健身美體 2", "就是健身美體 2", "健身美體", false, false);
        course_list[8] = new Course(8, "健身美體 3", "就是健身美體 3", "健身美體", false, false);
        for (int i=0; i<9; i++) {
            uid = mAuth.getInstance().getCurrentUser().getUid();
            mDB.child("members").child(uid).child("courses").child(Integer.toString(course_list[i].getId())).setValue(course_list[i]);
        }
    }
}
