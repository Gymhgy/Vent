package com.example.vent;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.vent.databinding.ActivityMainBinding;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.nav_view);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // as soon as the application opens the first
        // fragment should be shown to the user
        // in this case it is algorithm fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        Set<String> strings = new HashSet<>();
        strings.add("09/20/2022|My kids don't have enough to eat\nAnd I can't do ANYTHING");
        strings.add("09/21/2022|I care BUT I DONT CARE AT THE SAME TIME\nWHYWHYWHY");
        strings.add("09/23/2022|I TAKE THE TRAIN FOR 3 HRS\nI WORK for TEN HOURS A DAY\nIT'S NOT FOR SAFETY REASONS iF THE TRAIN GOES 70mph!!");
        SharedPreferences prefs = getSharedPreferences("public", MODE_PRIVATE);
        prefs.edit().putStringSet("vents", strings).apply();
    }
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        // By using switch we can easily get
        // the selected fragment
        // by using there id.
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.logbook) {
            selectedFragment = new LogbookFragment();
        } else if (itemId == R.id.explore) {
            selectedFragment = new ExploreFragment();
        }
        // It will help to replace the
        // one fragment to other.
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
        }
        return true;
    };
}