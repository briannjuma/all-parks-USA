package com.brayo.allparks;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.brayo.allparks.databinding.ActivityMainBinding;
import com.brayo.allparks.fragments.ParksFragment;
import com.brayo.allparks.fragments.ReferenceFragment;
import com.brayo.allparks.models.ParkViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ParkViewModel parkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Log.i(TAG, "Main Activity opened");

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String username = bundle.getString("username");
            String userId = bundle.getString("userId");
            setTitle("Welcome to All-parks " + username);
        }


       // Fragment switching
        Fragment referenceFragment = (Fragment) getSupportFragmentManager()
                .findFragmentById(R.id.referenceContainerView);
        referenceFragment.getActivity();

        BottomNavigationView bottomNavigationView = binding.bottomNavigation;
        //noinspection deprecation
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            Fragment selectedFragment = null;

            int id = item.getItemId();

            if (id == R.id.reference_nav_button) {
                // show reference fragment
                selectedFragment = ReferenceFragment.newInstance();

            } else if (id == R.id.parks_nav_button) {
                // show Park fragment
                selectedFragment = ParksFragment.newInstance();

            } else {
                selectedFragment = ReferenceFragment.newInstance();
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.referenceContainerView, selectedFragment)
                    .commit();

            return true;
        });
    }
}