package com.example.aninterface.ui.course;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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

public class CourseIntroductionFragment extends Fragment {
    private DatabaseReference mDB;
    private Bundle bundle;
    private Course course_now;
    private boolean is_add_to_menu;
    private TextView txt_course_introduction_name1;
    private TextView txt_course_introduction_content1;
    private Button btn_enter_course1;
    private ImageButton img_btn_like;
    private long count;
    private String uid;
    private String name;
    private String description;
    private int course_id;
    private FirebaseAuth mAuth;
    private VideoFragment videoFragment;
    private FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_courseintroduction, container, false);
        //mDB = FirebaseDatabase.getInstance().getReference();
        //uid = mAuth.getInstance().getCurrentUser().getUid();

        bundle = this.getArguments();
        course_now = new Course(bundle.getInt("course_id"), bundle.getString("name"), bundle.getString("description"), bundle.getString("theme_now"), false, false);
        name = bundle.getString("name");
        description = bundle.getString("description");
        course_id = bundle.getInt("course_id");

        txt_course_introduction_name1 = root.findViewById(R.id.txt_course_introduction_name1);
        txt_course_introduction_content1 = root.findViewById(R.id.txt_course_introduction_content1);
        btn_enter_course1 = root.findViewById(R.id.btn_enter_course1);
        img_btn_like = root.findViewById(R.id.img_btn_like);

        is_add_to_menu = false;

        txt_course_introduction_name1.setText(name);
        txt_course_introduction_content1.setText(description);

        btn_enter_course1 = (Button) root.findViewById(R.id.btn_enter_course1);
        btn_enter_course1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoFragment = new VideoFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, videoFragment).addToBackStack(null).commit();
            }
        });

        /*mDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                count = dataSnapshot.child("members").child(uid).child("menu").getChildrenCount();
                if (dataSnapshot.child("members").child(uid).child("courses").child(Integer.toString(course_now.getId())).child("is_add_to_menu").getValue().toString().equals("false")) {
                    is_add_to_menu = false;
                } else {
                    is_add_to_menu = true;
                }
                check_is_add_to_menu();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                
            }
        });*/

        img_btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_add_to_menu == false) {
                    is_add_to_menu = true;
                    mDB.child("members").child(uid).child("courses").child(Integer.toString(course_now.getId())).child("is_add_to_menu").setValue(true);
                    img_btn_like.setImageResource(R.drawable.heart);
                    mDB.child("members").child(uid).child("menu").child(Long.toString(count)).setValue(course_now.getName());
                    Toast.makeText(getActivity(), "已加入菜單", Toast.LENGTH_SHORT).show();
                } else {
                    is_add_to_menu = false;
                    mDB.child("members").child(uid).child("courses").child(Integer.toString(course_now.getId())).child("is_add_to_menu").setValue(false);
                    img_btn_like.setImageResource(R.drawable.favorite_24px);
                    for (int i=0; i<count; i++) {
                        final int finalI = i;
                        mDB.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("members").child(uid).child("menu").child(Integer.toString(finalI)).getValue().toString()!=null) {
                                    if (dataSnapshot.child("members").child(uid).child("menu").child(Integer.toString(finalI)).getValue().toString().equals(course_now.getName()) && dataSnapshot.child("members").child(uid).child("menu").child(Integer.toString(finalI)).getValue().toString()!=null) {
                                        mDB.child("members").child(uid).child("menu").child(Integer.toString(finalI)).removeValue();
                                    } else {
                                        return;
                                    }
                                } else {
                                    return;
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                    Toast.makeText(getActivity(), "已從菜單移除", Toast.LENGTH_SHORT).show();
                }
                // 要把愛心的圖案換成實心的
            }
        });

        return root;
    }

    public void check_is_add_to_menu() {
        if (is_add_to_menu == true) {
            img_btn_like.setImageResource(R.drawable.heart);
        } else {
            img_btn_like.setImageResource(R.drawable.favorite_24px);
        }
    }
}
