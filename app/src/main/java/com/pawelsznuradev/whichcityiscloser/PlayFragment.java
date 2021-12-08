package com.pawelsznuradev.whichcityiscloser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pawelsznuradev.whichcityiscloser.cityData.CitiesList;
import com.pawelsznuradev.whichcityiscloser.cityData.City;
import com.pawelsznuradev.whichcityiscloser.cityData.GeoDbApiService;

import java.util.ArrayList;
import java.util.Random;

public class PlayFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback, CitiesList {

    private static final String CITYQ = "CityQ";
    private static final String CITYA1 = "CityA1";
    private static final String CITYA2 = "CityA2";

    private static final String SCORE = "score";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String UNIT = "unit";

    private int score;

    Bundle bundle = new Bundle();

    // getting a list of cities
    ArrayList<City> listOfCities = createListOfCities();

    MapView mapView;
    GoogleMap map;

    City city1;
    City city2;
    City cityQuestion;

    String units;

    public PlayFragment() {
        // Required empty public constructor
    }

    public static CitiesList newInstance(int score) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putInt(SCORE, score);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            score = getArguments().getInt(SCORE);
            bundle.putInt(SCORE, score);
        }
        // life cycle awareness
        if (savedInstanceState == null) {
            // getting random numbers to select cities from the list
            Random rand = new Random();
            int random1 = rand.nextInt(listOfCities.size());
            int random2 = rand.nextInt(listOfCities.size());
            int random3 = rand.nextInt(listOfCities.size());

            while (random1 == random2) {
                // make sure the numbers are not the same
                random2 = rand.nextInt(listOfCities.size());
            }
            while (random1 == random3 || random2 == random3) {
                // make sure the numbers are not the same
                random3 = rand.nextInt(listOfCities.size());
            }

            city1 = listOfCities.get(random1);
            city2 = listOfCities.get(random2);
            cityQuestion = listOfCities.get(random3);
        } else {
            cityQuestion = savedInstanceState.getParcelable(CITYQ);
            city1 = savedInstanceState.getParcelable(CITYA1);
            city2 = savedInstanceState.getParcelable(CITYA2);
        }


        units = getUnits();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titlePlayFragment));

        // https://stackoverflow.com/a/19806967/10457515
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);

        Button btnCity1 = view.findViewById(R.id.btnCity1Play);
        btnCity1.setOnClickListener(this);

        Button btnCity2 = view.findViewById(R.id.btnCity2Play);
        btnCity2.setOnClickListener(this);

        TextView playText = view.findViewById(R.id.whichCityIsCloserPlayText);

        TextView scoreTextView = view.findViewById(R.id.scoreResultText);
        scoreTextView.setText(String.format("Your score: %s", score));


        // get data from API
        GeoDbApiService apiService = new GeoDbApiService(getContext(), bundle, units);
        apiService.getDistanceCities(cityQuestion.getId(), city1.getId(), "distanceCityA1");
        apiService.getDistanceCities(cityQuestion.getId(), city2.getId(), "distanceCityA2");

        // putting local data into bundle
        bundle.putString("cityQname", cityQuestion.getName());
        bundle.putString("cityA1name", city1.getName());
        bundle.putString("cityA2name", city2.getName());

        bundle.putParcelable("CityQ", cityQuestion);
        bundle.putParcelable("CityA1", city1);
        bundle.putParcelable("CityA2", city2);


        btnCity1.setText(city1.getName());
        btnCity2.setText(city2.getName());
        playText.setText(String.format("Which city is closer to %s?", cityQuestion.getName()));


        return view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCity1Play) {
            bundle.putInt("selectedCity", 1);
            Navigation.findNavController(view).navigate(R.id.action_playFragment_to_resultFragment, bundle);
        } else if (view.getId() == R.id.btnCity2Play) {
            bundle.putInt("selectedCity", 2);
            Navigation.findNavController(view).navigate(R.id.action_playFragment_to_resultFragment, bundle);
        }
    }

    private String getUnits() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(UNIT, "Imperial");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(CITYQ, cityQuestion);
        outState.putParcelable(CITYA1, city1);
        outState.putParcelable(CITYA2, city2);
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                LatLng cityCords = new LatLng(cityQuestion.getLatitude(), cityQuestion.getLongitude());
                String cityName = cityQuestion.getName();
                map.addMarker(new MarkerOptions()
                        .position(cityCords)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                        .title(cityName));

                map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(cityQuestion.getLatitude(), cityQuestion.getLongitude())));
            }
        });
    }

    @Override
    public void onResume() {
        // changing the title in case of lifecycle events like rotating the screen
        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titlePlayFragment));
        super.onResume();
        mapView.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}

