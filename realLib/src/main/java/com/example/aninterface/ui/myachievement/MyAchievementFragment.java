package com.example.aninterface.ui.myachievement;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import java.util.ArrayList;
import java.util.Objects;


import com.example.aninterface.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class MyAchievementFragment extends Fragment {

    private MyAchievementViewModel myMenuViewModel;
    private PieChart pieChart;
    PieData pieData;
    PieDataSet pieDataSet;
    ArrayList pieEntries;
    private DatabaseReference mDB;
    private String uid;
    private FirebaseAuth mAuth;
    private String str_has_done;
    private int aaacount;
    private int count;
    private double dbl_achievement;
    private float achievement;
    private int i;
    private static final String ACTIVITY_TAG="LogDemo";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //myMenuViewModel =
        ViewModelProviders.of(this).get(MyAchievementViewModel.class);
        View root = inflater.inflate(R.layout.fragment_my_achievement, container, false);

        pieChart = root.findViewById(R.id.pieChart);





        // 向Realtime DB 撈資料
        uid = mAuth.getInstance().getCurrentUser().getUid();
        mDB = FirebaseDatabase.getInstance().getReference();
        mDB.child("members").addListenerForSingleValueEvent(new ValueEventListener() {
            private static final String ACTIVITY_TAG="LogDemo";
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                aaacount = (int) dataSnapshot.child(uid).child("courses").getChildrenCount();
                Log.d(ACTIVITY_TAG, String.valueOf(aaacount));
                for(i=0;i<aaacount;i++){
                    str_has_done = dataSnapshot.child(uid).child("courses").child(String.valueOf(i)).child("has_done").getValue().toString();
                    if(str_has_done.equals("true"))
                    {
                        count+=1;
                    }
                };
                Log.d(ACTIVITY_TAG, "count: " + String.valueOf(count));
                achievement=(float) count/9;


                getEntries();

                pieDataSet = new PieDataSet(pieEntries, "");
                pieData = new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieDataSet.setSliceSpace(2f);
                pieDataSet.setValueTextColor(Color.WHITE);
                pieDataSet.setValueTextSize(0f);
                pieDataSet.setSliceSpace(5f);
                pieDataSet.setColors(PIE_COLORS);
                pieChart.getLegend().setEnabled(false); // bottom left legend
                Description description = new Description();
                description.setText("");
                pieChart.setDescription(description); // bottom right description label
                pieChart.setTouchEnabled(false); // prevent dragging

                /*pieChart.setDrawHoleEnabled(true); // hole in the center
                pieChart.setHoleColor(Color.rgb(228, 221, 206));

                pieChart.setTransparentCircleAlpha(0); // center circle transparency*/

                pieChart.animateXY(1400,1400);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
//        getEntries();
        return root;
    }
    public void getEntries(){
        Log.d(ACTIVITY_TAG, "getEntries: "+achievement);
        pieEntries = new ArrayList<>();
        pieEntries.add(new PieEntry((float)achievement, " "));
        pieEntries.add(new PieEntry((float)1-achievement, " "));

    }
    public static final int[] PIE_COLORS = {
            Color.rgb(183, 156, 128), Color.rgb(228, 221, 206)
    };

}