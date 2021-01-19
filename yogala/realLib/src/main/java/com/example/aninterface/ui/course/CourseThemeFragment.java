package com.example.aninterface.ui.course;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.aninterface.Course;
import com.example.aninterface.Member;
import com.example.aninterface.R;
import com.example.aninterface.ui.Video.VideoFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class CourseThemeFragment extends Fragment {

    private CourseThemeViewModel courseThemeViewModel;
    private DatabaseReference mDB;
    private FirebaseAuth mAuth;
    private FragmentManager fragmentManager;
    private CourseListFragment courseListFragment;
    private CourseIntroductionFragment courseIntroductionFragment;
    private Bundle bundle_from_course_theme;
    private Bundle bundle_from_toolbar;
    private Member member;
    private Spinner courseSpinner = null;
    private Spinner themeSpinner = null;
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
    private Course course_now;
    private String uid;
    private int count;
    private int position_id_selected;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //courseThemeViewModel = ViewModelProviders.of(this).get(CourseThemeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_coursetheme, container, false);
        //member = (Member) getActivity().getIntent().getSerializableExtra("Member");
        themeSpinner = (Spinner) root.findViewById(R.id.spinner_theme);
        courseSpinner = (Spinner) root.findViewById(R.id.spinner_course);
        btn_enter_course = (Button) root.findViewById(R.id.btn_enter_course2);

        //繫結介面卡和值
        themeAdapter = new ArrayAdapter<String>(CourseThemeFragment.this.getActivity(), android.R.layout.simple_spinner_item, theme);
        themeSpinner.setAdapter(themeAdapter);
        themeSpinner.setSelection(0, true);  //設定預設選中項
        theme_now = "身體放鬆";
        courseAdapter = new ArrayAdapter<String>(CourseThemeFragment.this.getActivity(), android.R.layout.simple_spinner_item, course[0]);
        courseSpinner.setAdapter(courseAdapter);
        courseSpinner.setSelection(0, true);  //預設選中第0個
        str_course_selected = "腰部放鬆";


        themeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                theme_now = themeSpinner.getSelectedItem().toString();
                courseAdapter = new ArrayAdapter<String>(
                        CourseThemeFragment.this.getActivity(), android.R.layout.simple_spinner_item, course[position]);
                courseSpinner.setAdapter(courseAdapter);
                themePosition = position;
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
                bundle_from_course_theme = new Bundle();
                bundle_from_course_theme.putString("name", str_course_selected);
                bundle_from_course_theme.putString("description", descriptions[position_id_selected]);
                bundle_from_course_theme.putString("theme", theme_now);
                bundle_from_course_theme.putInt("course_id", position_id_selected);
                courseIntroductionFragment = new CourseIntroductionFragment();
                courseIntroductionFragment.setArguments(bundle_from_course_theme);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, courseIntroductionFragment).addToBackStack(null).commit();
            }
        });

        return root;
    }

    public String[][] Input_courses() {
        mDB = FirebaseDatabase.getInstance().getReference();
        uid = mAuth.getInstance().getCurrentUser().getUid();
        count = 0;
        final String[][] course_input = new String[3][3];
        mDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        course_input[i][j] = dataSnapshot.child("members").child(uid).child("courses").child(Integer.toString(count)).child("name").getValue().toString();
                        count++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return course_input;
    }
}