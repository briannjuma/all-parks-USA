package com.brayo.allparks;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.brayo.allparks.databinding.ActivityParkListBinding;

public class ParkListActivity extends AppCompatActivity {
    private ActivityParkListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_park_list);

        Log.i(TAG, "List of National Parks Activity opened");



        if (getIntent().getStringExtra("state") != null) {
            String title = getIntent().getStringExtra("state");
            setTitle("National Parks in " + title);
        }
    }
}