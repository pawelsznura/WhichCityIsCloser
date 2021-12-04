package com.pawelsznuradev.whichcityiscloser.data;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Pawel Sznura on 24/11/2021.
 */
public class GeoDbApiService {


    // City Distance URL
    // BASEURL + CITIES + CityQid + DISTANCE + (optional DISTANCEUNIT + KM/MI + &) + TOCITYID + CityAid

    private final String BASEURL = "https://wft-geo-db.p.rapidapi.com/v1/geo/";
    private final String CITIES = "cities";
    private final String DISTANCE = "distance?";
    private final String DISTANCEUNIT = "distanceUnit=";
    private final String TOCITYID = "toCityId=";
    private final String NAMEPREFIX = "namePrefix=";
    private final String SORT = "sort=";
    private final String POPULATIONDESC = "-population";


    private final Map<String, String> headerArgs = new HashMap<String, String>() {{
        put("x-rapidapi-host", "wft-geo-db.p.rapidapi.com");
        put("x-rapidapi-key", "ce749f2f6dmsh27fdfbb7699816ep1dfcb4jsn587e74ca313f");
    }};

    Context context;
    RequestQueue queue;
    Bundle bundle;

    String distanceUnit;


    public GeoDbApiService(Context context, Bundle bundle, String units) {
        //empty constructor
        this.context = context;
        this.bundle = bundle;
        queue = Volley.newRequestQueue(context);

        if (units.equals("Metric")) {
            distanceUnit = "KM";
        } else {
            distanceUnit = "MI";
        }
    }

    public void getListOfCountriesWithCodes() {
        String[] countryCodes = Locale.getISOCountries();
        String[] countrynames = new String[countryCodes.length];
//        Log.e("country codes", Arrays.toString(countryCodes));
        for (int i = 0; i < countryCodes.length; i++) {
            countrynames[i] = new Locale("", countryCodes[i]).getDisplayName();
        }
//        Log.e("country name", Arrays.toString(countrynames));
    }

    public void getCityDetailsByID(String id) {
//        'https://wft-geo-db.p.rapidapi.com/v1/geo/cities/Q60'
        String url = BASEURL + CITIES + "/" + id;
//        Log.e("url", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e("response", response.toString());
                        try {
                            City city = new City(response.getJSONObject("data"));
                            Log.e(city.getName() + "lat", String.valueOf(city.getLatitude()));
                            Log.e(city.getName() + "long", String.valueOf(city.getLongitude()));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error on response", String.valueOf(error));
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerArgs;
            }
        };
        queue.add(jsonObjectRequest);
    }


    public void getCitiesListDetailsByName(String name) {
//        https://wft-geo-db.p.rapidapi.com/v1/geo/cities?namePrefix=London&sort=-population
        String url = BASEURL + CITIES + "?" + NAMEPREFIX + name + "&" + SORT + POPULATIONDESC;
//        Log.e("url", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.e("response", response.toString());
                        try {
                            JSONArray dataArray = response.getJSONArray("data");
                            for (int i = 0; i < dataArray.length(); i++) {

                                City city = new City(dataArray.getJSONObject(i));
                                Log.e(city.getName() + "lat", String.valueOf(city.getLatitude()));
                                Log.e(city.getName() + "long", String.valueOf(city.getLongitude()));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error on response", String.valueOf(error));
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerArgs;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public void getDistanceCities(int cityIdFrom, int cityIdTo, String bundleKey) {

        String url = BASEURL + CITIES + "/" + cityIdFrom + "/" + DISTANCE + DISTANCEUNIT + distanceUnit + "&" + TOCITYID + cityIdTo;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            bundle.putInt(bundleKey, response.getInt("data"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error on response", String.valueOf(error));
            }

        }

        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headerArgs;
            }

        };
        queue.add(jsonObjectRequest);
    }


}
