package com.gios.airindex.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gios.airindex.R;
import com.gios.airindex.model.AirIndexStation;

import java.util.List;

public class StationsAdapter extends ArrayAdapter<AirIndexStation> {
    private static final String CLASS_TAG = "StationsAdapter";

    private int resourceLayout;
    private Context context;
    private View view;
    private RelativeLayout relativeLayout;

    public StationsAdapter(@NonNull Context context, int resource, @NonNull List<AirIndexStation> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceLayout = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        view = convertView;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(resourceLayout, null);
        }


        AirIndexStation airIndexStation = getItem(position);
        if (airIndexStation != null) {
            TextView stationName = view.findViewById(R.id.stationNameValue);
            TextView indexLevelName = view.findViewById(R.id.indexLevelNameValue);
            TextView address = view.findViewById(R.id.stationAddressValue);
            TextView city = view.findViewById(R.id.stationCityValue);
            TextView calcDate = view.findViewById(R.id.calcDateValue);
            relativeLayout = view.findViewById(R.id.singleRow);

            if (stationName != null) {
                stationName.setText(airIndexStation.getStationName());
            }
            if (indexLevelName != null) {
                indexLevelName.setText(airIndexStation.getIndexLevelName());
                switch (airIndexStation.getIndexLevelName()) {
                    case "Bardzo dobry":
                        relativeLayout.setBackgroundColor(Color.parseColor("#57b108"));
                        break;
                    case "Dobry":
                        relativeLayout.setBackgroundColor(Color.parseColor("#b0dd10"));
                        break;
                    case "Umiarkowany":
                        relativeLayout.setBackgroundColor(Color.parseColor("#ffd911"));
                        break;
                    case "Dostateczny":
                        relativeLayout.setBackgroundColor(Color.parseColor("#e58100"));
                        break;
                    case "Zły":
                        relativeLayout.setBackgroundColor(Color.parseColor("#e50000"));
                        break;
                    case "Bardzo zły":
                        relativeLayout.setBackgroundColor(Color.parseColor("#990000"));
                        break;
                    case "Brak indeksu":
                        relativeLayout.setBackgroundColor(Color.parseColor("#bfbfbf"));
                        break;
                }

            }
            if (address != null) {
                address.setText(airIndexStation.getAddress());
            }
            if (city != null) {
                city.setText(airIndexStation.getCity());
            }
            if (calcDate != null) {
                calcDate.setText(airIndexStation.getCalcDate());
            }
        }

        return view;
    }

}
