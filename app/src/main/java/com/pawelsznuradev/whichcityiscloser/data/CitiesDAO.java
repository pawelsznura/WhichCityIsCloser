package com.pawelsznuradev.whichcityiscloser.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

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

    @Query("SELECT * FROM City WHERE id = :id")
    public City findById(int id);


}
