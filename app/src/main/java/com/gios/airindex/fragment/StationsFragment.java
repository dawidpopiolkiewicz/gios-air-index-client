package com.gios.airindex.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
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
 * The type Stations fragment.
 *
 * @author Dawid Popiołkiewicz
 */
public class StationsFragment extends Fragment {


    private ListView listView;
    private DatabaseHandler databaseHandler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stations, container, false);
    }


    @Override
    public void onStart() {
        initElements();
        setupAdapter();

        super.onStart();
    }

    private void initElements() {
        databaseHandler = new DatabaseHandler(getActivity().getBaseContext());
        listView = getActivity().findViewById(R.id.stationsListView);

    }

    private void setupAdapter() {
        final List<AirIndexStation> airIndexStations = databaseHandler.getAll();
        StationsAdapter stationsAdapter = new StationsAdapter(getActivity().getBaseContext(), R.layout.station_row, airIndexStations);
        listView.setAdapter(stationsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "Wybrano stacje: " + airIndexStations.get(position).getStationName(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
