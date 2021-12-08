package com.pawelsznuradev.whichcityiscloser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pawelsznuradev.whichcityiscloser.highscore.Highscore;
import com.pawelsznuradev.whichcityiscloser.highscore.HighscoreDao;
import com.pawelsznuradev.whichcityiscloser.highscore.HighscoreDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HighScoreFragment extends Fragment {

    List<Highscore> highscores = new ArrayList<>();

    public HighScoreFragment() {
        // Required empty public constructor
    }

    public static HighScoreFragment newInstance() {
        HighScoreFragment fragment = new HighScoreFragment();
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        // changing the title in case of lifecycle events like rotating the screen
        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titleHighScoresFragment));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // set the title in the action bar
        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titleHighScoresFragment));

        return inflater.inflate(R.layout.fragment_high_score, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HighscoreDatabase highscoreDatabase = HighscoreDatabase.getDatabase(getContext());

        HighscoreDao highscoreDao = highscoreDatabase.highscoreDao();

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // getting the data on the background thread
                highscores = highscoreDao.getAllHighscores();

                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        // passing the high score data to the recycler viewer
                        HighScoreRecyclerViewAdapter adapter = new HighScoreRecyclerViewAdapter(getContext(), highscores);

                        RecyclerView rvHighscores = view.findViewById(R.id.rv_highscores);

                        rvHighscores.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvHighscores.setAdapter(adapter);
                    }
                });
            }
        });

    }
}