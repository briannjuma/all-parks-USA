package com.brayo.allparks;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.brayo.allparks.databinding.ActivityStatereferenceBinding;

import java.util.Objects;

import javax.xml.validation.Validator;


public class StateReferenceActivity extends AppCompatActivity {
    private ActivityStatereferenceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_statereference);

        Log.i(TAG, "State Code Reference Activity opened");
        discoverParksButton();
    }

    private void discoverParksButton(){
        binding.discoverParksButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StateReferenceActivity.this, ParkListActivity.class);

                // Retrieve State Input
                String state = Objects.requireNonNull(binding.editTextStateCode).getText().toString().trim();

                // Validate input
                validateUserInput(intent, state);

            }

        });
    }

    private void validateUserInput(Intent intent, String state){
        if (state.isEmpty()){
            Toast.makeText(StateReferenceActivity.this,"Please check your state input it should not be empty",Toast.LENGTH_SHORT).show();
        } else {
            intent.putExtra("state", state);
            startActivity(intent);
            Log.i(TAG,"Navigating to Park List Activity ...");
            Toast.makeText(StateReferenceActivity.this,"Navigating to List of National Parks",Toast.LENGTH_SHORT).show();
        }
    }
}