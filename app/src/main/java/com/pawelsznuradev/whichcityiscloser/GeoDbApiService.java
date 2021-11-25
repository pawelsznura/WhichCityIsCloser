package com.pawelsznuradev.whichcityiscloser;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pawel Sznura on 24/11/2021.
 */
public class GeoDbApiService {


    // City Distance URL
    // BASEURL + CITIES + CityQid + DISTANCE + (optional DISTANCEUNIT + KM/MI + &) + TOCITYID + CityAid

    private final String BASEURL = "https://wft-geo-db.p.rapidapi.com/v1/geo/";
    private final String CITIES = "cities/";
    private final String DISTANCE = "distance?";
    private final String DISTANCEUNIT = "distanceUnit=";
    private final String TOCITYID = "toCityId=";
    private final Map<String, String> HeaderArgs = new HashMap<String, String>() {{
        put("x-rapidapi-host", "wft-geo-db.p.rapidapi.com");
        put("x-rapidapi-key", "ce749f2f6dmsh27fdfbb7699816ep1dfcb4jsn587e74ca313f");
    }};

    Context context;
    RequestQueue queue;


    public GeoDbApiService(Context context) {
        //empty constructor
        this.context = context;
        queue = Volley.newRequestQueue(context);
    }

//    "https://wft-geo-db.p.rapidapi.com/v1/geo/cities/45633/distance?distanceUnit=KM&toCityId=144809"

    public int getDistanceCities(int cityIdFrom, int cityIdTo) {
        String url = BASEURL + CITIES + cityIdFrom + "/" + DISTANCE + TOCITYID + cityIdTo;
        // no idea why is this a one element final array but IDE said so
        final int[] distance = new int[1];
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            distance[0] = response.getInt("data");

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
                return HeaderArgs;
            }
        };
        queue.add(jsonObjectRequest);
        return distance[0];
    }

    @NonNull
    public StringRequest getExampleCity() {

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
                Map<String, String> params = new HashMap<>();
                params.put("x-rapidapi-host", "wft-geo-db.p.rapidapi.com");
                params.put("x-rapidapi-key", "ce749f2f6dmsh27fdfbb7699816ep1dfcb4jsn587e74ca313f");

                return params;
            }
        };
        return stringRequest;
    }

}
