package com.pawelsznuradev.whichcityiscloser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlayFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    int distanceCity1;


    Bundle bundle = new Bundle();


    public PlayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlayFragment newInstance(String param1, String param2) {
        PlayFragment fragment = new PlayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // changing the title in case of lifecycle events like rotating the screen
        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titlePlayFragment));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play, container, false);

        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titlePlayFragment));

        Random rand = new Random();

        // getting a sample list of cities
        ArrayList<City> listOfCities = createListOfCities();


        int random1 = rand.nextInt(listOfCities.size());
        int random2 = rand.nextInt(listOfCities.size());
        int random3 = rand.nextInt(listOfCities.size());

        while (random1 == random2) {
//            make sure the numbers are not the same
            random2 = rand.nextInt(listOfCities.size());
        }
        while (random1 == random3 || random2 == random3) {
//            make sure the numbers are not the same
            random3 = rand.nextInt(listOfCities.size());
        }


        City city1 = listOfCities.get(random1);
        City city2 = listOfCities.get(random2);
        City cityQuestion = listOfCities.get(random3);


        // get data from API

        GeoDbApiService apiService = new GeoDbApiService(getContext());

        distanceCity1 = apiService.getDistanceCities(cityQuestion.getId(), city1.getId());
        int distanceCity2 = apiService.getDistanceCities(cityQuestion.getId(), city2.getId());

        // putting the data into the bundle
        bundle.putString("cityQname", cityQuestion.getName());
        bundle.putString("cityA1name", city1.getName());
        bundle.putString("cityA2name", city2.getName());
        bundle.putInt("distanceCityA1", distanceCity1);
        bundle.putInt("distanceCityA2", distanceCity2);


        Button btnCity1 = view.findViewById(R.id.btnCity1Play);
        btnCity1.setText(city1.getName());
        btnCity1.setOnClickListener(this);

        Button btnCity2 = view.findViewById(R.id.btnCity2Play);
        btnCity2.setText(city2.getName());
        btnCity2.setOnClickListener(this);

        TextView playText = view.findViewById(R.id.whichCityIsCloserPlayText);
        playText.setText(String.format("Which city is closer to %s?", cityQuestion.getName()));

        return view;
    }

    private void putIntoBundle() {

    }


    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnCity1Play) {
//            putIntoBundle();
            Navigation.findNavController(view).navigate(R.id.action_playFragment_to_resultFragment, bundle);
        } else if (view.getId() == R.id.btnCity2Play) {
            Navigation.findNavController(view).navigate(R.id.action_playFragment_to_resultFragment, bundle);
        }
    }

    public ArrayList<City> createListOfCities() {
//        this function creates a small list of cities for easier testing
        ArrayList<City> arrayList = new ArrayList<>();
        arrayList.add(new City(45633, "Q84", "London", 1.0, 1.0));
        arrayList.add(new City(144809, "Q2379199", "Edinburgh", 1.0, 1.0));
        arrayList.add(new City(84640, "Q585", "Oslo", 1.0, 1.0));
        arrayList.add(new City(47394, "Q36405", "Aberdeen", 1.0, 1.0));
        arrayList.add(new City(3453309, "Q64", "Berlin", 1.0, 1.0));
        arrayList.add(new City(144571, "Q90", "Paris", 1.0, 1.0));
        arrayList.add(new City(3097353, "Q727", "Amsterdam", 1.0, 1.0));
        arrayList.add(new City(3452878, "Q220", "Rome", 1.0, 1.0));
        arrayList.add(new City(30988, "Q2807", "Madrid", 1.0, 1.0));
        arrayList.add(new City(160049, "Q597", "Lisbon", 1.0, 1.0));
        arrayList.add(new City(3453194, "Q649", "Moscow", 1.0, 1.0));
        arrayList.add(new City(92150, "Q270", "Warsaw", 1.0, 1.0));
        arrayList.add(new City(51643, "Q1781", "Budapest", 1.0, 1.0));
        arrayList.add(new City(3453053, "Q1741", "Vienna", 1.0, 1.0));
        arrayList.add(new City(25261, "Q1748", "Copenhagen", 1.0, 1.0));
        arrayList.add(new City(34314, "Q1757", "Helsinki", 1.0, 1.0));
        arrayList.add(new City(3020322, "Q1754", "Stockholm", 1.0, 1.0));
        arrayList.add(new City(48676, "Q1524", "Athens", 1.0, 1.0));
        arrayList.add(new City(3453093, "Q19660", "Bucharest", 1.0, 1.0));
        arrayList.add(new City(7448, "Q472", "Sofia", 1.0, 1.0));

        return arrayList;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.e("onSaveInstanceState", String.valueOf(distanceCity1));

        Log.e("distanceCity1", String.valueOf(distanceCity1));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        Log.e("onPause", String.valueOf(distanceCity1));

        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("onStop", String.valueOf(distanceCity1));

        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.e("onDestroy", String.valueOf(distanceCity1));

        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        Log.e("onDestroyView", String.valueOf(distanceCity1));

        super.onDestroyView();
    }


}

