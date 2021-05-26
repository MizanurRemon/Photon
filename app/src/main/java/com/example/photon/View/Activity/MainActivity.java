package com.example.photon.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.photon.R;
import com.example.photon.View.Fragment.HomeScreen_fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new HomeScreen_fragment()).commit();
        }

        setContentView(R.layout.activity_main);
    }
}