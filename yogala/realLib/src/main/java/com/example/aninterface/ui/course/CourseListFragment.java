package com.example.aninterface.ui.course;

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

import com.example.aninterface.Course;
import com.example.aninterface.Member;
import com.example.aninterface.R;

public class CourseListFragment extends Fragment {
    private CourseIntroductionFragment courseIntroductionFragment;
    private FragmentManager fragmentManager;
    private Bundle bundle;
    private Course[] arr_course_list;
    private TextView txt_course_list;
    private Button btn_back_pain;
    private Button btn_arm_pain;
    private Button btn_butt_pain;
    private Course course;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_courselist, container, false);

        bundle = this.getArguments();
        //arr_from_course_theme = (Object[]) bundle.getSerializable("from_course_theme");
        //member = (Member)arr_from_course_theme[0];

        txt_course_list = root.findViewById(R.id.txt_course_list);
        btn_back_pain = root.findViewById(R.id.btn_back_pain);
        btn_arm_pain = root.findViewById(R.id.btn_arm_pain);
        btn_butt_pain = root.findViewById(R.id.btn_butt_pain);

        arr_course_list = (Course[]) bundle.getSerializable("from_course_theme");
        txt_course_list.setText(arr_course_list[0].getTheme());
        btn_back_pain.setText(arr_course_list[0].getName());
        btn_arm_pain.setText(arr_course_list[1].getName());
        btn_butt_pain.setText(arr_course_list[2].getName());

        btn_back_pain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course = arr_course_list[0];
                bundle = new Bundle();
                bundle.putSerializable("from_course_list", course);
                courseIntroductionFragment = new CourseIntroductionFragment();
                courseIntroductionFragment.setArguments(bundle);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, courseIntroductionFragment).addToBackStack(null).commit();
            }
        });
        btn_arm_pain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course = arr_course_list[1];
                bundle = new Bundle();
                bundle.putSerializable("from_course_list", course);
                courseIntroductionFragment = new CourseIntroductionFragment();
                courseIntroductionFragment.setArguments(bundle);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, courseIntroductionFragment).addToBackStack(null).commit();
            }
        });
        btn_butt_pain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course = arr_course_list[2];
                bundle = new Bundle();
                bundle.putSerializable("from_course_list", course);
                courseIntroductionFragment = new CourseIntroductionFragment();
                courseIntroductionFragment.setArguments(bundle);
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, courseIntroductionFragment).addToBackStack(null).commit();
            }
        });

        return root;
    }
}
