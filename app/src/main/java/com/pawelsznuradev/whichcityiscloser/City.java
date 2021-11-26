package com.pawelsznuradev.whichcityiscloser;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pawel Sznura on 24/11/2021.
 */

public class City {
    /**
     * Example:
     * "id":45633
     * "wikiDataId":"Q84"
     * "type":"CITY"
     * "city":"London"
     * "name":"London"
     * "country":"United Kingdom"
     * "countryCode":"GB"
     * "region":"England"
     * "regionCode":"ENG"
     * "latitude":51.507222222
     * "longitude":-0.1275
     * "population":8908081
     */

    private int id;
    private String wikiDataId;
    private String type;
    private String city;
    private String name;
    private String country;
    private String countryCode;
    private String region;
    private String regionCode;
    private double latitude;
    private double longitude;
    private int population;


    public City(JSONObject response) {
        try {
            id = response.getInt("id");
            wikiDataId = response.getString("wikiDataId");
            type = response.getString("type");
            city = response.getString("city");
            name = response.getString("name");
            country = response.getString("country");
            countryCode = response.getString("countryCode");
            region = response.getString("region");
            regionCode = response.getString("regionCode");
            latitude = response.getDouble("latitude");
            longitude = response.getDouble("longitude");
            population = response.getInt("population");

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }
}
