package com.example.aninterface.ui.mymenu;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.aninterface.Course;
import com.example.aninterface.LoginActivity;
import com.example.aninterface.Member;
import com.example.aninterface.R;
import com.example.aninterface.ToolbarActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyMenuFragment extends Fragment {

    private MyMenuViewModel myMenuViewModel;
    private DatabaseReference mDB;
    private ArrayList<Course> courses_in_menu;
    private ConstraintLayout lyt_my_menu;
    private Spinner spinner;
    private String uid;
    private int count;
    private String[] arr_menu;
    private FirebaseAuth mAuth;
    private ArrayAdapter<String> menu_list;
    private Course course_selected;
    private TextView course_name;
    private TextView course_intro;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_menu, container, false);
        course_name = (TextView) root.findViewById(R.id.txt_course_introduction_name1);
        course_intro = (TextView) root.findViewById(R.id.txt_course_introduction_content1);
        spinner = (Spinner) root.findViewById(R.id.spinner_menu);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                mDB.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        course_selected = new Course(0, dataSnapshot.child("Courses").child(arr_menu[position]).getKey().toString(), dataSnapshot.child("Courses").child(arr_menu[position]).getValue().toString(), "theme", false, false);
                        Log.e("check course name", course_selected.getName());
                        course_name.setText(course_selected.getName());
                        course_intro.setText(course_selected.getDescription());
                }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        lyt_my_menu = (ConstraintLayout) root.findViewById(R.id.lyt_my_menu);
        mDB = FirebaseDatabase.getInstance().getReference();
        uid = mAuth.getInstance().getCurrentUser().getUid();
        mDB.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = (int) dataSnapshot.child("members").child(uid).child("menu").getChildrenCount();
                arr_menu = new String[count];
                if (count == 0) {
                    Toast.makeText(getActivity(), "Your menu is empty", Toast.LENGTH_SHORT).show();
                } else {
                    arr_menu = new String[Math.toIntExact(count)];
                    for (int i=0; i<count; i++) {
                        arr_menu[i] = dataSnapshot.child("members").child(uid).child("menu").child(Integer.toString(i)).getValue().toString();
                    }
                    menu_list = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arr_menu);
                    spinner.setAdapter(menu_list);
                    spinner.setSelection(0);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return root;
    }
}
