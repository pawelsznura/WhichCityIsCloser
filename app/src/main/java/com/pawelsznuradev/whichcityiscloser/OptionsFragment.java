package com.pawelsznuradev.whichcityiscloser;

import static androidx.core.content.ContextCompat.getSystemService;

import static com.pawelsznuradev.whichcityiscloser.R.string.highscoresDeletedMessage;
import static com.pawelsznuradev.whichcityiscloser.R.string.usernameSaved;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.pawelsznuradev.whichcityiscloser.highscore.HighscoreDao;
import com.pawelsznuradev.whichcityiscloser.highscore.HighscoreDatabase;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class OptionsFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private EditText editTextUsername;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String USERNAME = "username";
    public static final String UNIT = "unit";
    public static final String DARKMODE = "darkmode";


    public OptionsFragment() {
        // Required empty public constructor
    }

    public static OptionsFragment newInstance() {
        OptionsFragment fragment = new OptionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // check what the user selected previously
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

        Button buttonSaveUsername = (Button) view.findViewById(R.id.btnUsernameSaveOptions);
        buttonSaveUsername.setOnClickListener(this);

        editTextUsername = (EditText) view.findViewById(R.id.editTextUsernameOptions);
        // set inside the textview the previous username
        editTextUsername.setText(getUsername());

        Button buttonDeleteHighScores = (Button) view.findViewById(R.id.btnDeleteHighScoresOptions);
        buttonDeleteHighScores.setOnClickListener(this);

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Spinner Units metric = 0, imperial = 1
        if (i == 0) {
            saveUnits("Metric");
        } else if (i == 1) {
            saveUnits("Imperial");
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
            hideKeyboard();
        } else if (v.getId() == R.id.btnDeleteHighScoresOptions) {
            askToConfirmDeleteHS();
        }
    }

    private void askToConfirmDeleteHS() {
        // based on    https://stackoverflow.com/a/36747528/10457515
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setCancelable(true);
        builder.setTitle(R.string.askToConfirmDeleteHsTitle);
        builder.setMessage(R.string.askToConfirmDeleteHsMessage);
        builder.setPositiveButton(R.string.confirm,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteHighScores();
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void deleteHighScores() {
        HighscoreDatabase highscoreDatabase = HighscoreDatabase.getDatabase(getContext());
        HighscoreDao highscoreDao = highscoreDatabase.highscoreDao();

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {

                highscoreDao.nukeWHOLETable();
            }
        });

        Toast.makeText(getContext(), highscoresDeletedMessage, Toast.LENGTH_SHORT).show();
    }

    private void hideKeyboard() {
        // based on https://stackoverflow.com/a/9981958/10457515
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }


    private void saveUsername() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(USERNAME, editTextUsername.getText().toString());
        editor.apply();

        Toast.makeText(getContext(), usernameSaved, Toast.LENGTH_SHORT).show();

    }

    private String getUsername() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERNAME, "");
    }
}