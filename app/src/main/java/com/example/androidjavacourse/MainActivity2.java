package com.example.androidjavacourse;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.androidjavacourse.databinding.ActivityMain2Binding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    AirplanemodeReceiver apm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main2);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main2);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Other stuff
        apm = new AirplanemodeReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);

        // Dokumentaatio tapa
        boolean listenToBroadcastsFromOtherApps = false;
        int receiverFlags = listenToBroadcastsFromOtherApps
                ? ContextCompat.RECEIVER_EXPORTED
                : ContextCompat.RECEIVER_NOT_EXPORTED;

        ContextCompat.registerReceiver(this, apm, filter, receiverFlags);

        // Opettajan tapa
        //this.registerReceiver(apm, filter);

        // Muista käydä laittamassa manifestista receiver:exported false
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(apm);
    }
}