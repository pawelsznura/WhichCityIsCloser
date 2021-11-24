package com.pawelsznuradev.whichcityiscloser;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pawel Sznura on 24/11/2021.
 */

public class City {

    int id;
    String wikiDataId;
    String name;
    double latitude;
    double longitude;


    public City(JSONObject response) {
        try {
            id = response.getInt("id");
            wikiDataId = response.getString("wikiDataId");
            name = response.getString("name");
            latitude = response.getDouble("latitude");
            longitude = response.getDouble("longitude");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public City(int id, String wikiDataId, String name, double latitude, double longitude) {
        this.id = id;
        this.wikiDataId = wikiDataId;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    @Override
    public String toString() {
        return "name = " + name;
    }
}
