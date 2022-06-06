package com.brayo.allparks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.brayo.allparks.databinding.ActivityUserprofileBinding;

public class UserProfileActivity extends AppCompatActivity {
    private ActivityUserprofileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_userprofile);


    }
}