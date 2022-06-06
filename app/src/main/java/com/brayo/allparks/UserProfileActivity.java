package com.brayo.allparks;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.brayo.allparks.databinding.ActivityUserprofileBinding;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {
    private ActivityUserprofileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_userprofile);

        Log.i(TAG, "State Code Reference Activity opened");

        binding.discoverParksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UserProfileActivity.this, ParkListActivity.class);
                startActivity(intent1);
                Log.i(TAG,"Navigating to Park List Activity ...");
                Toast.makeText(UserProfileActivity.this,"Navigating to List of National Parks",Toast.LENGTH_SHORT).show();

            }

        });
    }
}