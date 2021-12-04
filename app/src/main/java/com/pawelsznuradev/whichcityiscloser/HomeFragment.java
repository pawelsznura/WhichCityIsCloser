package com.pawelsznuradev.whichcityiscloser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titleHomeFragment));


        // based on Week 3 lecture slides
        Button btnPlay = view.findViewById(R.id.btnplayhome);
        btnPlay.setOnClickListener(this);

        Button btnQuit = view.findViewById(R.id.btnquithome);
        btnQuit.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnplayhome) {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_playFragment);
        } else if (v.getId() == R.id.btnquithome) {
            System.exit(0);
        }


    }

    @Override
    public void onResume() {
        super.onResume();

        // set title during the first startup and in case of lifecycle events like rotating the screen
        // based on
        // NOT_A_PROGRAMMER, 2015. Change ActionBar title using Fragments. [online]. Stack Overflow. Available from: https://stackoverflow.com/a/28453012/ [Accessed 21 October 2021].
        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titleHomeFragment));
    }
}