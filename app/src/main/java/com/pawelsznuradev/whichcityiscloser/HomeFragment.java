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
import com.pawelsznuradev.whichcityiscloser.cityData.City;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment implements View.OnClickListener {


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

        // populate cities database
        CitiesDatabase citiesDatabase = CitiesDatabase.getDatabase(getContext());
        CitiesDAO citiesDAO = citiesDatabase.citiesDAO();

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<City> cachedCities = citiesDAO.getAllCities();
                if (cachedCities.size() == 0) {
                    citiesDAO.insert(createListOfCities());
                }
            }
        });

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

    public ArrayList<City> createListOfCities() {
        ArrayList<City> arrayList = new ArrayList<>();
        arrayList.add(new City(45633, "Q84", "London", -0.1275, 51.507222222));
        arrayList.add(new City(144809, "Q2379199", "Edinburgh", -3.19333, 55.94973));
        arrayList.add(new City(84640, "Q585", "Oslo", 10.752777777, 59.911111111));
        arrayList.add(new City(47394, "Q36405", "Aberdeen", -2.1, 57.15));
        arrayList.add(new City(3453309, "Q64", "Berlin", 13.383333333, 52.516666666));
        arrayList.add(new City(144571, "Q90", "Paris", 2.351388888, 48.856944444));
        arrayList.add(new City(3097353, "Q727", "Amsterdam", 4.9, 52.383333333));
        arrayList.add(new City(3452878, "Q220", "Rome", 12.482777777, 41.893055555));
        arrayList.add(new City(30988, "Q2807", "Madrid", -3.691944444, 40.418888888));
        arrayList.add(new City(160049, "Q597", "Lisbon", -9.13333, 38.71667));
        arrayList.add(new City(3453194, "Q649", "Moscow", 37.617777777, 55.755833333));
        arrayList.add(new City(92150, "Q270", "Warsaw", 21.033333333, 52.216666666));
        arrayList.add(new City(51643, "Q1781", "Budapest", 19.040833333, 47.498333333));
        arrayList.add(new City(3453053, "Q1741", "Vienna", 16.373064, 48.20833));
        arrayList.add(new City(25261, "Q1748", "Copenhagen", 12.568888888, 55.676111111));
        arrayList.add(new City(34314, "Q1757", "Helsinki", 24.93417, 60.17556));
        arrayList.add(new City(3020322, "Q1754", "Stockholm", 18.068611111, 59.329444444));
        arrayList.add(new City(48676, "Q1524", "Athens", 23.72784, 37.98376));
        arrayList.add(new City(3453093, "Q19660", "Bucharest", 26.083333333, 44.4));
        arrayList.add(new City(7448, "Q472", "Sofia", 23.321726, 42.697886));

        return arrayList;
    }
}