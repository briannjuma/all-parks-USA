package com.brayo.allparks;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.brayo.allparks.databinding.ActivityParkListBinding;

import com.brayo.allparks.adapter.ParkListAdapter;

public class ParkListActivity extends AppCompatActivity {
    private ActivityParkListBinding binding;
    private final String[] nationalParkNames = {"Allegheny Portage Railroad National", "American Memorial Park", "Amistad National Recreation Area", "Anacostia Park", "Andersonville Historic", "Bluestone Scenic", "Mount Rainer National", "Roosevelt National Recreation"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_park_list);

        Log.i(TAG, "List of National Parks Activity opened");

        // Retrieve data from state reference Activity and edit Action Bar title
        if (getIntent().getStringExtra("state") != null) {
            String title = getIntent().getStringExtra("state");
            setTitle("National Parks in " + title);
            Toast.makeText(ParkListActivity.this,"Displaying List of National Parks",Toast.LENGTH_SHORT).show();
        }

        initializeAdapter();
        initializeOnClickListener();
    }

    private void initializeAdapter(){
        ParkListAdapter adapter = new ParkListAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, nationalParkNames);
        binding.parksListView.setAdapter(adapter);
    }

    private void initializeOnClickListener(){
        binding.parksListView.setOnClickListener((adapterView, view, i, l) -> {
            String nationalPark = ((TextView)view).getText().toString();
        });
    }
}