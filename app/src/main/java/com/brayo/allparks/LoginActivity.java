package com.brayo.allparks;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.brayo.allparks.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        Log.i(TAG, "Log in Activity opened");

        // log in
        binding.emailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                Log.i(TAG,"Navigating to Main Activity ...");
                Toast.makeText(LoginActivity.this,"Discover National Parks",Toast.LENGTH_SHORT).show();
            }
        });

        // create account
        binding.createAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
                Log.i(TAG,"Navigating to Sign up Activity ...");
                Toast.makeText(LoginActivity.this,"Create an account with us",Toast.LENGTH_SHORT).show();
            }
        });
    }
}