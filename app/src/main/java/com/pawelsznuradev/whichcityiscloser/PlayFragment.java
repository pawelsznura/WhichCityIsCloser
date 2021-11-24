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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

        Map<String, String> headers = new HashMap<>();
        headers.put("x-rapidapi-host", "wft-geo-db.p.rapidapi.com");
        headers.put("x-rapidapi-key", "ce749f2f6dmsh27fdfbb7699816ep1dfcb4jsn587e74ca313f");

        String url = "https://wft-geo-db.p.rapidapi.com/v1/geo/cities?limit=5&offset=0";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data = jsonObject.getJSONArray("data");
                            City city = new City(data.getJSONObject(0));
                            Log.e("city", city.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error on response", "error");
            }

        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("x-rapidapi-host", "wft-geo-db.p.rapidapi.com");
                params.put("x-rapidapi-key", "ce749f2f6dmsh27fdfbb7699816ep1dfcb4jsn587e74ca313f");

                return params;
            }
        };
        ;

        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(stringRequest);


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


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCity1Play) {
            Navigation.findNavController(view).navigate(R.id.action_playFragment_to_resultFragment);
        } else if (view.getId() == R.id.btnCity2Play) {
            Navigation.findNavController(view).navigate(R.id.action_playFragment_to_resultFragment);
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


}

