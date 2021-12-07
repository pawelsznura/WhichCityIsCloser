package com.pawelsznuradev.whichcityiscloser.highscore;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.pawelsznuradev.whichcityiscloser.cityData.City;

import java.util.List;

/**
 * Created by Pawel Sznura on 04/12/2021.
 */
@Dao
public interface HighscoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Highscore highscore);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(List<Highscore> highscores);

    @Delete
    public void delete(Highscore highscore);

    @Delete
    public void delete(List<Highscore> highscores);

    @Query("DELETE FROM HIGHSCORE")
    public void nukeWHOLETable();

    @Query("SELECT * FROM HIGHSCORE WHERE uid = :uid")
    public Highscore findByUid(int uid);

    @Query("SELECT * FROM HIGHSCORE ORDER BY score DESC")
    public List<Highscore> getAllHighscores();




}
