package com.gios.airindex.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gios.airindex.MainActivity;
import com.gios.airindex.R;
import com.gios.airindex.adapter.StationsAdapter;
import com.gios.airindex.dao.DatabaseHandler;
import com.gios.airindex.model.AirIndexStation;

import java.util.List;

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
        List<AirIndexStation> airIndexStations = databaseHandler.getAll();
        StationsAdapter stationsAdapter = new StationsAdapter(getActivity().getBaseContext(), R.layout.station_row, airIndexStations);
        listView.setAdapter(stationsAdapter);
    }
}
