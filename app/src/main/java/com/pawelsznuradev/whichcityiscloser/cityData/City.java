package com.pawelsznuradev.whichcityiscloser.cityData;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pawel Sznura on 24/11/2021.
 */

@Entity(tableName = "City")
public class City implements Parcelable {
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

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

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

    protected City(Parcel in) {
        id = in.readInt();
        wikiDataId = in.readString();
        type = in.readString();
        city = in.readString();
        name = in.readString();
        country = in.readString();
        countryCode = in.readString();
        region = in.readString();
        regionCode = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        population = in.readInt();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(wikiDataId);
        parcel.writeString(type);
        parcel.writeString(city);
        parcel.writeString(name);
        parcel.writeString(country);
        parcel.writeString(countryCode);
        parcel.writeString(region);
        parcel.writeString(regionCode);
        parcel.writeDouble(latitude);
        parcel.writeDouble(longitude);
        parcel.writeInt(population);

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
