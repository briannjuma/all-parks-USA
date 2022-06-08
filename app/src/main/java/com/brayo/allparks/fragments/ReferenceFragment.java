package com.brayo.allparks.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brayo.allparks.R;

public class ReferenceFragment extends Fragment {

    public ReferenceFragment() {
        // Required empty public constructor
    }


    public static ReferenceFragment newInstance() {
        ReferenceFragment fragment = new ReferenceFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reference, container, false);
    }
}