package com.pawelsznuradev.whichcityiscloser;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OptionsFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText editTextUsername;
    private Button buttonSaveUsername;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String UNIT = "unit";
    public static final String DARKMODE = "darkmode";


    public OptionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OptionsFragment newInstance(String param1, String param2) {
        OptionsFragment fragment = new OptionsFragment();
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
    public void onResume() {
        super.onResume();

        // changing the title in case of lifecycle events like rotating the screen, turning on dark mode
        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titleOptionsFragment));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_options, container, false);

        ((MainActivity) getActivity()).setAppBarTitle(getContext().getString(R.string.titleOptionsFragment));


        // Spinner for units selection Metric or Imperial
        // based on
        // ANDROID DEVELOPERS, 2021. Spinners  |  Android Developers. [online]. Android Developers. Available from: https://developer.android.com/guide/topics/ui/controls/spinner [Accessed 21 October 2021].
        Spinner spinner = (Spinner) view.findViewById(R.id.units_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.units_array, R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        int selectedUnit;
        if (getUnits().equals("Imperial")) {
            selectedUnit = 1;
        } else {
            selectedUnit = 0;
        }

        spinner.setSelection(selectedUnit);


        // Switch for dark mode
        // based on
        // KOTHARI, S., 2013. android.widget.Switch - on/off event listener?. [online]. Stack Overflow. Available from: https://stackoverflow.com/a/14556746 [Accessed 21 October 2021].
        Switch darkModeSwitch = (Switch) view.findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.i("Dark mode = ", String.valueOf(b));
                if (b) { // dark mode ON
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    saveDarkMode(true);
                } else { // dark mode OFF
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    saveDarkMode(false);
                }
            }
        });

        darkModeSwitch.setChecked(getDarkmode());

        editTextUsername = (EditText) view.findViewById(R.id.editTextUsernameOptions);
        buttonSaveUsername = (Button) view.findViewById(R.id.btnUsernameSaveOptions);

        buttonSaveUsername.setOnClickListener(this);

        editTextUsername.setText(getUsername());


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Spinner Units metric = 0, imperial = 1
        if (i == 0) {
            saveUnits("Metric");
            Log.i("Unit selected", "Metric");
        } else if (i == 1) {
            saveUnits("Imperial");
            Log.i("Unit selected", "Imperial");
        }
    }

    private void saveDarkMode(Boolean x) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(DARKMODE, x);
        editor.apply();
    }

    private boolean getDarkmode() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(DARKMODE, false);
    }

    private void saveUnits(String unit) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(UNIT, unit);
        editor.apply();
    }

    private String getUnits() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(UNIT, "");
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnUsernameSaveOptions) {
            saveUsername();
        }
    }

    private void saveUsername() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USERNAME, editTextUsername.getText().toString());
        editor.apply();
    }

    private String getUsername() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERNAME, "");
    }
}