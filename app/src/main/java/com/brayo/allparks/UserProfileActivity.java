package com.brayo.allparks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.brayo.allparks.databinding.ActivityUserprofileBinding;

import java.util.Objects;

public class UserProfileActivity extends AppCompatActivity {
    private ActivityUserprofileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_userprofile);

        binding.discoverParksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(UserProfileActivity.this, ParkListActivity.class);
                startActivity(intent1);






            }

        });
    }
}