package com.example.aninterface.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aninterface.LoginActivity;
import com.example.aninterface.MainActivity;
import com.example.aninterface.Member;

import com.example.aninterface.R;
import com.example.aninterface.ToolbarActivity;
import com.example.aninterface.ui.Video.VideoFragment;
import com.example.aninterface.ui.myachievement.MyAchievementFragment;
import com.example.aninterface.ui.mymenu.MyMenuFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private TextView txt_user_name;
    private Button btn_profile_edit;
    private String uid;
    private FirebaseAuth mAuth;
    private DatabaseReference mDB;
    private String email;
    private String firstname;
    private String lastname;
    private Button btn_course_loved;
    private Button btn_course_completed;
    private Button btn_logout;
    private MyMenuFragment myMenuFragment;
    private MyAchievementFragment myAchievementFragment;
    private FragmentManager fragmentManager;
    private Intent intent;
    private SharedPreferences sharedPreferences;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        txt_user_name = (TextView) root.findViewById(R.id.txt_user_name);
        btn_profile_edit = (Button)root.findViewById(R.id.btn_profile_edit);
        btn_course_loved = (Button) root.findViewById(R.id.btn_course_loved);
        btn_course_completed = (Button) root.findViewById(R.id.btn_course_completed);
        btn_logout = (Button) root.findViewById(R.id.btn_logout);

        // 向Realtime DB 撈資料
        uid = mAuth.getInstance().getCurrentUser().getUid();
        mDB = FirebaseDatabase.getInstance().getReference();
        mDB.child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                email = dataSnapshot.child(uid).child("email").getValue().toString();
                firstname = dataSnapshot.child(uid).child("firstname").getValue().toString();
                lastname = dataSnapshot.child(uid).child("lastname").getValue().toString();
                txt_user_name.setText(firstname+lastname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //btn_profile_edit.setOnClickListener();

        btn_course_loved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myMenuFragment = new MyMenuFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, myMenuFragment).addToBackStack(null).commit();
            }
        });
        btn_course_completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAchievementFragment = new MyAchievementFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, myAchievementFragment).addToBackStack(null).commit();
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE).edit();
                editor.clear();
                editor.commit();
                intent = new Intent();
                intent.setClass(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return root;
    }

}
