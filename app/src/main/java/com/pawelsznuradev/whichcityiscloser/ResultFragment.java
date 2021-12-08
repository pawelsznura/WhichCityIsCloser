package com.pawelsznuradev.whichcityiscloser;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
import com.pawelsznuradev.whichcityiscloser.cityData.City;
import com.pawelsznuradev.whichcityiscloser.highscore.Highscore;
import com.pawelsznuradev.whichcityiscloser.highscore.HighscoreDao;
import com.pawelsznuradev.whichcityiscloser.highscore.HighscoreDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private static final String DISTANCECITYA1 = "distanceCityA1";
    private static final String DISTANCECITYA2 = "distanceCityA2";
    private static final String SELECTEDCITY = "selectedCity";
    private static final String SCORE = "score";
    private static final String CITYQ = "CityQ";
    private static final String CITYA1 = "CityA1";
    private static final String CITYA2 = "CityA2";


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String UNIT = "unit";
    public static final String USERNAME = "username";


    // TODO: Rename and change types of parameters

    private int distanceCityA1;
    private int distanceCityA2;
    private int selectedCity;
    private int score;
    private City cityQ;
    private City cityA1;
    private City cityA2;

    private boolean userWasCorrect;

    Bundle bundle = new Bundle();

    String units;


    MapView mapView;
    GoogleMap googleMap;


    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cityQ the city in the question.
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(int distanceCityA1, int distanceCityA2, int selectedCity, int score, City cityQ, City cityA1, City cityA2) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();

        args.putInt(DISTANCECITYA1, distanceCityA1);
        args.putInt(DISTANCECITYA2, distanceCityA2);
        args.putInt(SELECTEDCITY, selectedCity);
        args.putInt(SCORE, score);
        args.putParcelable(CITYQ, cityQ);
        args.putParcelable(CITYA1, cityA1);
        args.putParcelable(CITYA2, cityA2);


        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            distanceCityA1 = getArguments().getInt(DISTANCECITYA1);
            distanceCityA2 = getArguments().getInt(DISTANCECITYA2);
            selectedCity = getArguments().getInt(SELECTEDCITY);
            score = getArguments().getInt(SCORE);
            cityQ = getArguments().getParcelable(CITYQ);
            cityA1 = getArguments().getParcelable(CITYA1);
            cityA2 = getArguments().getParcelable(CITYA2);

            if (distanceCityA1 < distanceCityA2) {
                // A1 is correct
                if (selectedCity == 1) {
                    // user was correct
                    userWasCorrect = true;
                    score++;
                } else if (selectedCity == 2) {
                    // user was wrong
                    userWasCorrect = false;
                }
            } else {
                // A2 is correct
                if (selectedCity == 2) {
                    // user was correct
                    userWasCorrect = true;
                    score++;
                } else if (selectedCity == 1) {
                    // user was wrong
                    userWasCorrect = false;
                }
            }

            units = getUnits();
        }
    }

    private String getUnits() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        if (sharedPreferences.getString(UNIT, "Imperial") == "Metric") {
            return "KM";
        } else {
            return "MI";
        }


    }

    private String getUsername() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERNAME, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titleResultFragment));


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);


        Button btnNext = view.findViewById(R.id.btnNextResult);
        btnNext.setOnClickListener(this);

        Button btnShare = view.findViewById(R.id.btnShare);

        if (!userWasCorrect) {
            btnNext.setText(R.string.tryAgainText);
            btnShare.setVisibility(View.VISIBLE);
            btnShare.setOnClickListener(this);
        }
        // if user was wrong then change text of the button

        TextView cityQuestionTextView = view.findViewById(R.id.rightWrongResultText);

        if (distanceCityA1 < distanceCityA2) {
            // A1 is correct
            if (selectedCity == 1) {
                // user was correct
                cityQuestionTextView.setText(R.string.correctText);
            } else if (selectedCity == 2) {
                // user was wrong
                cityQuestionTextView.setText(R.string.wrongText);
                saveHighscore();
            } else {
                // selection error
                cityQuestionTextView.setText(R.string.errorText);
            }
        } else {
            // A2 is correct
            if (selectedCity == 2) {
                // user was correct
                cityQuestionTextView.setText(R.string.answerCorrect);
            } else if (selectedCity == 1) {
                // user was wrong
                cityQuestionTextView.setText(R.string.answerWrong);
            } else {
                // selection error
                cityQuestionTextView.setText(R.string.error);
            }
        }


        TextView city1resultTextView = view.findViewById(R.id.city1ResultText);
        city1resultTextView.setText(String.format(getString(R.string.distanceResultText), cityA1.getName(), distanceCityA1, units, cityQ.getName()));

        TextView city2resultTextView = view.findViewById(R.id.city2ResultText);
        city2resultTextView.setText(String.format(getString(R.string.distanceResultText), cityA2.getName(), distanceCityA2, units, cityQ.getName()));

        TextView scoreTextView = view.findViewById(R.id.scoreResultText);
        scoreTextView.setText(String.format("Your score: %s", score));

        bundle.putInt(SCORE, score);


        return view;
    }

    private void saveHighscore() {
        // get database for storing the highscore
        HighscoreDatabase highscoreDatabase = HighscoreDatabase.getDatabase(getContext());
        // get the DAO for Highscores
        HighscoreDao highscoreDao = highscoreDatabase.highscoreDao();

        String name = getUsername();

        Highscore highscore = new Highscore(name, score);

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                highscoreDao.insert(highscore);
            }
        });


//        Log.e("Highscore", highscore.toString());

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNextResult) {
            // if user was right then navigate to play fragment
            if (userWasCorrect) {
                Navigation.findNavController(view).navigate(R.id.action_resultFragment_to_playFragment, bundle);

            } else {
                // if user was wrong then navigate to home fragment
                Navigation.findNavController(view).navigate(R.id.action_resultFragment_to_homeFragment, bundle);
            }
        } else if (view.getId() == R.id.btnShare) {
            onShare();
        }
    }

    private void onShare() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, String.format(getString(R.string.shareText), score));
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(cityQ.getLatitude(), cityQ.getLongitude()))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                .title(cityQ.getName()));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(cityA1.getLatitude(), cityA1.getLongitude()))
                .title(cityA1.getName()));

        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(cityA2.getLatitude(), cityA2.getLongitude()))
                .title(cityA2.getName()));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(cityQ.getLatitude(), cityQ.getLongitude())));

    }


    @Override
    public void onResume() {
        // changing the title in case of lifecycle events like rotating the screen
        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titleResultFragment));

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