package com.example.glowup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Main2 extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    AppontmentFragment appontmentFragment = new AppontmentFragment();
    ReviewsFragment reviewsFragment = new ReviewsFragment();

    private static final int MENU_HOME = R.id.home;
    private static final int MENU_APPOINTMENT = R.id.appointment;
    private static final int MENU_REVIEWS = R.id.reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,homeFragment).commit();


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == MENU_HOME) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, homeFragment).commit();
                    return true;
                } else if (itemId == MENU_APPOINTMENT) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, appontmentFragment).commit();
                    return true;
                }else if (itemId == MENU_REVIEWS) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, reviewsFragment).commit();
                    return true;
                }
                return false;

            }

        });
    }
}