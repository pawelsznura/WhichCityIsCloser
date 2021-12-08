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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HighScoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HighScoreFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<Highscore> highscores = new ArrayList<>();


    public HighScoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HighScoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HighScoreFragment newInstance(String param1, String param2) {
        HighScoreFragment fragment = new HighScoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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

//        highscores = highscoreDao.getAllHighscores();

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

                        // passing the high score data to the recycler viewer on the UI thread
                        HighScoreRecyclerViewAdapter adapter = new HighScoreRecyclerViewAdapter(getContext(), highscores);

                        RecyclerView rvHighscores = view.findViewById(R.id.rv_highscores);

                        rvHighscores.setLayoutManager(new LinearLayoutManager(getContext()));
                        rvHighscores.setAdapter(adapter);
                    }
                });
            }
        });


//        new Handler(Looper.getMainLooper()).post(new Runnable() {
//            @Override
//            public void run() {
//                        highscores = highscoreDao.getAllHighscores();
//
//            }
//        });


//        Log.e("list ", highscores.toString());


    }
}