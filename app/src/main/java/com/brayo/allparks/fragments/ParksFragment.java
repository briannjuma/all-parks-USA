package com.brayo.allparks.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.brayo.allparks.R;
import com.brayo.allparks.adapter.OnParkClickListener;
import com.brayo.allparks.adapter.ParkRecyclerViewAdapter;
import com.brayo.allparks.data.AsyncResponse;
import com.brayo.allparks.data.Repository;
import com.brayo.allparks.models.Park;
import com.brayo.allparks.models.ParkViewModel;

import java.util.ArrayList;
import java.util.List;


public class ParksFragment extends Fragment implements OnParkClickListener {
    private RecyclerView recyclerView;
    private ParkRecyclerViewAdapter parkRecyclerViewAdapter;
    private List<Park> parkList;
    private ParkViewModel parkViewModel;
    private CardView cardView;
    private EditText stateCodeET;
    private ImageButton searchButton;
    private String code= "";

    public ParksFragment() {
        // Required empty public constructor
        }


    public static ParksFragment newInstance() {
        ParksFragment fragment = new ParksFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        parkList = new ArrayList<>();

        // search view widget
//        cardView = getView().findViewById(R.id.cardview);
//        stateCodeET = getView().findViewById(R.id.floating_state_value_et);
//        searchButton = getView().findViewById(R.id.floating_search_button);
//
//        searchButton.setOnClickListener(v -> {
//            String stateCode =stateCodeET.getText().toString().trim();
//            if (!TextUtils.isEmpty(stateCode)) {
//                code = stateCode;
//                parkViewModel.selectCode(code);
//                stateCodeET.setText("");
//            }
//        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       //search view widget
        cardView = view.findViewById(R.id.cardview);
        stateCodeET = view.findViewById(R.id.floating_state_value_et);
        searchButton = view.findViewById(R.id.floating_search_button);

        searchButton.setOnClickListener(v -> {
            String stateCode =stateCodeET.getText().toString().trim();
            if (!TextUtils.isEmpty(stateCode)) {
                code = stateCode;
                parkViewModel.selectCode(code);
                stateCodeET.setText("");
            }
        });


        parkViewModel = new ViewModelProvider(requireActivity())
                .get(ParkViewModel.class);
        if (parkViewModel.getParks().getValue() != null) {
            parkList = parkViewModel.getParks().getValue();

        }
        Repository.getParks(parks -> {
            parkRecyclerViewAdapter = new ParkRecyclerViewAdapter(parks, this);
            recyclerView.setAdapter(parkRecyclerViewAdapter);

        }, code);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_parks, container,false);
        recyclerView = view.findViewById(R.id.park_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onParkClicked(Park park) {
        Log.d("Park", "onParkClicked: " + park.getName());

        parkViewModel.setSelectedPark(park);
        //noinspection deprecation
        getFragmentManager().beginTransaction()
                .replace(R.id.park_fragment, DetailsFragment.newInstance())
                .commit();

    }
}

