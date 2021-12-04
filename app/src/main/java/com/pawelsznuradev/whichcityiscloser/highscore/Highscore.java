package com.pawelsznuradev.whichcityiscloser.highscore;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Pawel Sznura on 04/12/2021.
 */

@Entity(tableName = "highscore")
public class Highscore {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int uid;

    private String name;
    private int score;

    public Highscore(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("name: %s     score: %d", name, score);

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
