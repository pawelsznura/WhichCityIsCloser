package com.pawelsznuradev.whichcityiscloser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.pawelsznuradev.whichcityiscloser.cityData.CitiesDAO;
import com.pawelsznuradev.whichcityiscloser.cityData.CitiesDatabase;
import com.pawelsznuradev.whichcityiscloser.cityData.CitiesList;
import com.pawelsznuradev.whichcityiscloser.cityData.City;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment implements View.OnClickListener, CitiesList {


    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titleHomeFragment));


        // based on Week 3 lecture slides
        Button btnPlay = view.findViewById(R.id.btnplayhome);
        btnPlay.setOnClickListener(this);

        Button btnQuit = view.findViewById(R.id.btnquithome);
        btnQuit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        for now the CitiesDatabase is not in use
//        I run into a few bugs I could not solve when getting data from database and using it for the api request
//        mainly the data from the database did not arrive on time for the api request
//        // populate cities database
//        CitiesDatabase citiesDatabase = CitiesDatabase.getDatabase(getContext());
//        CitiesDAO citiesDAO = citiesDatabase.citiesDAO();
//
//        Executor executor = Executors.newSingleThreadExecutor();
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                List<City> cachedCities = citiesDAO.getAllCities();
//                if (cachedCities.size() == 0) {
//                    citiesDAO.insert(createListOfCities());
//                }
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnplayhome) {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_playFragment);
        } else if (v.getId() == R.id.btnquithome) {
            System.exit(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // set title during the first startup and in case of lifecycle events like rotating the screen
        // based on
        // NOT_A_PROGRAMMER, 2015. Change ActionBar title using Fragments. [online]. Stack Overflow. Available from: https://stackoverflow.com/a/28453012/ [Accessed 21 October 2021].
        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titleHomeFragment));
    }

}