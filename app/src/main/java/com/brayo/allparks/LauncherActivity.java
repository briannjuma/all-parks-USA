package com.brayo.allparks;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.brayo.allparks.databinding.ActivityLauncherBinding;

public class LauncherActivity extends AppCompatActivity {
    private ActivityLauncherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launcher);

        Log.i(TAG, "Launcher Activity opened");

        binding.launcherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LauncherActivity.this, UserProfileActivity.class);
                startActivity(intent);
                Log.i(TAG,"Navigating to State Code Reference Activity ...");
                Toast.makeText(LauncherActivity.this,"Navigating to State Code Reference",Toast.LENGTH_SHORT).show();
            }
        });
    }
}