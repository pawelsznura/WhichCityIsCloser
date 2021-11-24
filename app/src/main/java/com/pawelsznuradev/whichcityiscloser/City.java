package com.pawelsznuradev.whichcityiscloser;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pawel Sznura on 24/11/2021.
 */

public class City {

    private int id;
    private String wikiDataId;
    private String name;
    private double latitude;
    private double longitude;


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

    public City(int id,
                String wikiDataId,
                String name,
                double longitude,
                double latitude) {
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


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWikiDataId() {
        return wikiDataId;
    }

    public void setWikiDataId(String wikiDataId) {
        this.wikiDataId = wikiDataId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
