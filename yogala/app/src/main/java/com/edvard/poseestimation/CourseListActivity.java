package com.edvard.poseestimation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class CourseListActivity extends AppCompatActivity {
    private Spinner courseSpinner = null;
    private android.widget.Spinner themeSpinner = null;
    private Button btn_enter_course;
    ArrayAdapter<String> themeAdapter = null;
    ArrayAdapter<String> courseAdapter = null;
    private static int themePosition = 0;
    private static int coursePosition = 0;
    private String[] theme = new String[]{"身體放鬆", "早晨甦醒", "健身美體"};
    private String[][] course = new String[][]{{"腰部放鬆", "頸部放鬆", "腿部放鬆"}, {"早晨甦醒 1", "早晨甦醒 2", "早晨甦醒 3"}, {"健身美體 1", "健身美體 2", "健身美體 3"}};
    private String[] descriptions = new String[]{"放鬆您的腰部，\n舒緩一天久坐的疲勞。", "就是睡前舒壓 2", "就是睡前舒壓 3", "就是早晨甦醒 1", "就是早晨甦醒 2", "就是早晨甦醒 3", "就是健身美體 1", "就是健身美體 2", "就是健身美體 3"};
    private String[][] course_test;
    private String theme_now;
    private String str_course_selected;
    private String uid;
    private int count;
    private int position_id_selected;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        //courseThemeViewModel = ViewModelProviders.of(this).get(CourseThemeViewModel.class);
        //View root = inflater.inflate(com.example.aninterface.R.layout.fragment_coursetheme, container, false);
        //member = (Member) getActivity().getIntent().getSerializableExtra("Member");
        themeSpinner = (Spinner)findViewById(R.id.spinner_theme);
        courseSpinner = (Spinner)findViewById(R.id.spinner_course);
        btn_enter_course = (Button)findViewById(R.id.btn_enter_course2);

        //繫結介面卡和值
        themeAdapter = new ArrayAdapter<String>(CourseListActivity.this, android.R.layout.simple_spinner_item, theme);
        themeSpinner.setAdapter(themeAdapter);
        themeSpinner.setSelection(0, true);  //設定預設選中項
        theme_now = "身體放鬆";
        courseAdapter = new ArrayAdapter<String>(CourseListActivity.this, android.R.layout.simple_spinner_item, course[0]);
        courseSpinner.setAdapter(courseAdapter);
        courseSpinner.setSelection(0, true);  //預設選中第0個
        str_course_selected = "腰部放鬆";


        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                theme_now = themeSpinner.getSelectedItem().toString();
                courseAdapter = new ArrayAdapter<String>(CourseListActivity.this, android.R.layout.simple_spinner_item, course[position]);
                courseSpinner.setAdapter(courseAdapter);
                themePosition = position;
                //if(position==1){
                   // pose pose1 = new pose();
                  //  pose1 .setPose(1) ;
                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }

        });

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_course_selected = courseSpinner.getSelectedItem().toString();
                coursePosition = position;
                position_id_selected = 3 * themePosition + coursePosition;
                /*mDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        course_now = new Course(position_id_selected, dataSnapshot.child("members").child(uid).child("courses").child(Integer.toString(position_id_selected)).child("name").getValue().toString(), dataSnapshot.child("members").child(uid).child("courses").child(Integer.toString(position_id_selected)).child("description").getValue().toString(), dataSnapshot.child("members").child(uid).child("courses").child(Integer.toString(position_id_selected)).child("theme").getValue().toString(), false, false);
                        Log.e("check course", Integer.toString(coursePosition));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_enter_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.putExtra("name", str_course_selected);
                intent.putExtra("description", descriptions[position_id_selected]);
                intent.putExtra("theme", theme_now);
                intent.putExtra("course_id", position_id_selected);
                intent.setClass(CourseListActivity.this, VideoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}