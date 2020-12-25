package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.aninterface.ui.course.CourseThemeFragment;
import com.example.aninterface.ui.myachievement.MyAchievementFragment;
import com.example.aninterface.ui.mymenu.MyMenuFragment;
import com.example.aninterface.ui.profile.ProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ToolbarActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ProfileFragment profileFragment;
    private CourseThemeFragment courseThemeFragment;
    private MyAchievementFragment myAchievementFragment;
    private MyMenuFragment myMenuFragment;
    private Intent intent;
    private Bundle bundle;
    private Member member;
    private FirebaseAuth mAuth;
    private String uid;
    private DatabaseReference mDB;
    private int count;
    private String[][] course = new String[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //uid = mAuth.getInstance().getCurrentUser().getUid();

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.nav_host_fragment, new CourseThemeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_course_theme);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_toolbar_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId == R.id.nav_course_theme) {
            courseThemeFragment = new CourseThemeFragment();
            bundle = new Bundle();
            bundle.putSerializable("course", course);
            courseThemeFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, courseThemeFragment).addToBackStack(null).commit();
            navigationView.setCheckedItem(R.id.nav_course_theme);
        } else if (itemId == R.id.nav_my_menu) {
            myMenuFragment = new MyMenuFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myMenuFragment).addToBackStack(null).commit();
            navigationView.setCheckedItem(R.id.nav_my_menu);
        } else if (itemId == R.id.nav_my_achievement) {
            myAchievementFragment = new MyAchievementFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myAchievementFragment).addToBackStack(null).commit();
            navigationView.setCheckedItem(R.id.nav_my_achievement);
        } else if (itemId == R.id.nav_profile) {
            profileFragment = new ProfileFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, profileFragment).addToBackStack(null).commit();
            getSupportFragmentManager().beginTransaction().addToBackStack(profileFragment.getClass().getName());
            navigationView.setCheckedItem(R.id.nav_profile);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
