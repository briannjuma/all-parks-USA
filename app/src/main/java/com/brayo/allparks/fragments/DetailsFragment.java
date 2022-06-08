package com.brayo.allparks.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brayo.allparks.R;
import com.brayo.allparks.adapter.ViewPageAdapter;
import com.brayo.allparks.models.ParkViewModel;

public class DetailsFragment extends Fragment {
    private ParkViewModel parkViewModel;
    private ViewPageAdapter viewPageAdapter;
    private ViewPager2 viewPager;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance() {
        DetailsFragment fragment = new DetailsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.details_viewpager);

        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);

        parkViewModel.getSelectedPark().observe(getViewLifecycleOwner(),park -> {
            viewPageAdapter = new ViewPageAdapter(park.getImages());
            viewPager.setAdapter(viewPageAdapter);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }
}