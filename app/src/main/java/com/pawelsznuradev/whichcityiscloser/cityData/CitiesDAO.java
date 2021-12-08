package com.pawelsznuradev.whichcityiscloser.cityData;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pawelsznuradev.whichcityiscloser.highscore.Highscore;

import java.util.List;

/**
 * Created by Pawel Sznura on 30/11/2021.
 */

@Dao
public interface CitiesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(City city);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(List<City> cities);

    @Delete
    public void delete(City city);

    @Delete
    public void delete(List<City> cities);

    @Query("SELECT * FROM City WHERE uid = :uid")
    public City findByUId(int uid);

    @Query("SELECT * FROM City ")
    public List<City> getAllCities();

    @Query("SELECT COUNT(*) FROM City")
    public int getCount();


}
