package com.pawelsznuradev.whichcityiscloser;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResultFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String CITYQNAME = "cityQname";
    private static final String CITYA1NAME = "cityA1name";
    private static final String CITYA2NAME = "cityA2name";
    private static final String DISTANCECITYA1 = "distanceCityA1";
    private static final String DISTANCECITYA2 = "distanceCityA2";

    // TODO: Rename and change types of parameters
    private String cityQname;
    private String cityA1name;
    private String cityA2name;
    private int distanceCityA1;
    private int distanceCityA2;

    public ResultFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cityQname name of the city in the question.
     * @return A new instance of fragment ResultFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(String cityQname, String cityA1name, String cityA2name, int distanceCityA1, int distanceCityA2) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putString(CITYQNAME, cityQname);
        args.putString(CITYA1NAME, cityA1name);
        args.putString(CITYA2NAME, cityA2name);
        args.putInt(DISTANCECITYA1, distanceCityA1);
        args.putInt(DISTANCECITYA2, distanceCityA2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        // changing the title in case of lifecycle events like rotating the screen
        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titleResultFragment));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            cityQname = getArguments().getString(CITYQNAME);
            cityA1name = getArguments().getString(CITYA1NAME);
            cityA2name = getArguments().getString(CITYA2NAME);
            distanceCityA1 = getArguments().getInt(DISTANCECITYA1);
            distanceCityA2 = getArguments().getInt(DISTANCECITYA2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titleResultFragment));

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        Button btnNext = view.findViewById(R.id.btnNextResult);
        btnNext.setOnClickListener(this);
        // if user was wrong then change text of the button

        TextView city1resultTextView = view.findViewById(R.id.city1ResultText);
        city1resultTextView.setText(String.format("%s is %d miles away", cityA1name, distanceCityA1));

        TextView city2resultTextView = view.findViewById(R.id.city2ResultText);
        city2resultTextView.setText(String.format("%s is %d miles away", cityA2name, distanceCityA2));

        return view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNextResult) {
            // if user was right then navigate to play fragment
            Navigation.findNavController(view).navigate(R.id.action_resultFragment_to_playFragment);
            // if user was wrong then navigate to home fragment
//            Navigation.findNavController(view).navigate(R.id.action_resultFragment_to_homeFragment);
        }
    }
}