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
import com.pawelsznuradev.whichcityiscloser.cityData.CitiesDAO;
import com.pawelsznuradev.whichcityiscloser.cityData.CitiesDatabase;
import com.pawelsznuradev.whichcityiscloser.cityData.City;
import com.pawelsznuradev.whichcityiscloser.cityData.GeoDbApiService;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    private static final String SCORE = "score";

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String UNIT = "unit";


    private int score;


    Bundle bundle = new Bundle();

    // getting a sample list of cities
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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param score score .
     * @return A new instance of fragment PlayFragment.
     */
    public static PlayFragment newInstance(int score) {
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

        Random rand = new Random();
        // get cities from database
        CitiesDatabase citiesDatabase = CitiesDatabase.getDatabase(getContext());
        CitiesDAO citiesDAO = citiesDatabase.citiesDAO();

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                final int sizeCities = citiesDAO.getCount();
                final int random1 = rand.nextInt(sizeCities);
                int random2 = rand.nextInt(sizeCities);
                int random3 = rand.nextInt(sizeCities);

                while (random1 == random2) {
//            make sure the numbers are not the same
                    random2 = rand.nextInt(sizeCities);
                }
                while (random1 == random3 || random2 == random3) {
//            make sure the numbers are not the same
                    random3 = rand.nextInt(sizeCities);
                }

                cityQuestion = citiesDAO.findByUId(random3);
                city1 = citiesDAO.findByUId(random1);
                city2 = citiesDAO.findByUId(random2);
            }
        });


//        int random1 = rand.nextInt(listOfCities.size());
//        int random2 = rand.nextInt(listOfCities.size());
//        int random3 = rand.nextInt(listOfCities.size());
//
//        while (random1 == random2) {
////            make sure the numbers are not the same
//            random2 = rand.nextInt(listOfCities.size());
//        }
//        while (random1 == random3 || random2 == random3) {
////            make sure the numbers are not the same
//            random3 = rand.nextInt(listOfCities.size());
//        }


//        city1 = listOfCities.get(random1);
//        city2 = listOfCities.get(random2);
//        cityQuestion = listOfCities.get(random3);

        units = getUnits();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titlePlayFragment));


//        https://stackoverflow.com/a/19806967/10457515
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


        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {

            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {

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


                    }
                });

            }
        });


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

    public ArrayList<City> createListOfCities() {
//        this function creates a small list of cities for easier testing
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

        mapView.onResume();
        super.onResume();
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

