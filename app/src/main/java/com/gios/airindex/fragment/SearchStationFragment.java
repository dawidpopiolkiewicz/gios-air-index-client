package com.gios.airindex.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gios.airindex.R;
import com.gios.airindex.adapter.StationsAdapter;
import com.gios.airindex.dao.DatabaseHandler;
import com.gios.airindex.model.AirIndexStation;

import java.util.List;


/**
 * The type Search station fragment.
 *
 * @author Dawid Popiołkiewicz
 */
public class SearchStationFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private final String DATA_DOWNLOADED = "Dane zostały pobrane";
    private final String NO_DATA = "Brak danych o wybranych parametrach";
    private final String CHOOSE_FILTER = "Wybierz filtr szukania";

    private View view;
    private ListView listView;
    private DatabaseHandler databaseHandler;
    private Runnable runnable;
    private Handler handler;
    private Button searchButton;
    private Switch citySwitch, indexSwitch;
    private Spinner citySpinner;
    private Spinner indexSpinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search_station, container, false);
        initElements();
        disableElements();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void initElements() {
        databaseHandler = new DatabaseHandler(getActivity().getBaseContext());
        listView = view.findViewById(R.id.stationsListView);
        indexSwitch = view.findViewById(R.id.indexSwitch);
        searchButton = view.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        citySwitch = view.findViewById(R.id.citySwitch);
        indexSwitch = view.findViewById(R.id.indexSwitch);
        citySpinner = view.findViewById(R.id.citySpinner);
        indexSpinner = view.findViewById(R.id.indexValueSpinner);
        citySwitch.setOnCheckedChangeListener(this);
        indexSwitch.setOnCheckedChangeListener(this);
    }


    private void disableElements() {
        citySwitch.setChecked(false);
        citySpinner.setEnabled(false);
        indexSwitch.setChecked(false);
        indexSpinner.setEnabled(false);
    }

    private void loadData() {
        handler = new Handler();
        runnable = () -> {
            if (getActivity() != null) {
                List<String> allCities = databaseHandler.getAllCities();
                ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, allCities);
                cityAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                citySpinner.setAdapter(cityAdapter);
                List<String> allIndexes = databaseHandler.getAllIndexes();
                ArrayAdapter<String> indexAdapter = new ArrayAdapter<>(getActivity().getBaseContext(), android.R.layout.simple_spinner_item, allIndexes);
                indexAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                indexSpinner.setAdapter(indexAdapter);
                Toast.makeText(getContext(), DATA_DOWNLOADED,
                        Toast.LENGTH_SHORT).show();
            }
        };
        handler.postDelayed(runnable, 0);

    }

    @Override
    public void onClick(View v) {
        if (citySwitch.isChecked() && indexSwitch.isChecked()) {
            handler = new Handler();
            runnable = () -> {
                List<AirIndexStation> stationListByCity = databaseHandler.getStationListByCityAndIndex(citySpinner.getSelectedItem().toString(), indexSpinner.getSelectedItem().toString());
                if (stationListByCity.size() == 0) {
                    Toast.makeText(getContext(), NO_DATA,
                            Toast.LENGTH_LONG).show();
                } else {
                    setupAdapter(stationListByCity);
                    Toast.makeText(getContext(), DATA_DOWNLOADED,
                            Toast.LENGTH_SHORT).show();
                }
            };
            handler.postDelayed(runnable, 0);

        } else if (citySwitch.isChecked() && !indexSwitch.isChecked()) {

            handler = new Handler();
            runnable = () -> {
                List<AirIndexStation> stationListByCity = databaseHandler.getStationListByCity(citySpinner.getSelectedItem().toString());
                if (stationListByCity.size() == 0) {
                    Toast.makeText(getContext(), NO_DATA,
                            Toast.LENGTH_LONG).show();
                } else {
                    setupAdapter(stationListByCity);
                    Toast.makeText(getContext(), DATA_DOWNLOADED,
                            Toast.LENGTH_SHORT).show();
                }
            };

        } else if (!citySwitch.isChecked() && indexSwitch.isChecked()) {
            handler = new Handler();
            runnable = () -> {
                List<AirIndexStation> stationListByIndex = databaseHandler.getStationListByIndex(indexSpinner.getSelectedItem().toString());
                if (stationListByIndex.size() == 0) {
                    Toast.makeText(getContext(), NO_DATA,
                            Toast.LENGTH_LONG).show();
                } else {
                    setupAdapter(stationListByIndex);
                    Toast.makeText(getContext(), DATA_DOWNLOADED,
                            Toast.LENGTH_SHORT).show();

                }
            };
        } else if (!citySwitch.isChecked() && !indexSwitch.isChecked()) {
            Toast.makeText(getContext(), CHOOSE_FILTER,
                    Toast.LENGTH_LONG).show();
        }

        handler.postDelayed(runnable, 0);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (citySwitch.isChecked()) {
            citySpinner.setEnabled(true);
        } else {
            citySpinner.setEnabled(false);
        }
        if (indexSwitch.isChecked()) {
            indexSpinner.setEnabled(true);
        } else {
            indexSpinner.setEnabled(false);
        }

    }


    private void setupAdapter(final List<AirIndexStation> airIndexStations) {
        StationsAdapter stationsAdapter = new StationsAdapter(getActivity().getBaseContext(), R.layout.station_row, airIndexStations);
        listView.setAdapter(stationsAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(getActivity(), "Wybrano stacje: " + airIndexStations.get(position).getStationName(), Toast.LENGTH_SHORT).show());
    }

}
