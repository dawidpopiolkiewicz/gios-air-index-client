package com.gios.airindex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.gios.airindex.api.ApiClient;
import com.gios.airindex.dao.DatabaseHandler;
import com.gios.airindex.fragment.SearchStationFragment;
import com.gios.airindex.fragment.StartFragment;
import com.gios.airindex.fragment.StationsFragment;
import com.gios.airindex.model.AirIndexStation;
import com.gios.airindex.model.AqIndex;
import com.gios.airindex.model.Station;
import com.gios.airindex.service.StationService;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.gios.airindex.R.string.navigation_drawer_close;

/**
 * The type Main activity.
 *
 * @author Dawid Popiołkiewicz
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private final static String CLASS_TAG = "MainActivity";
    private DrawerLayout drawerLayout;
    private DatabaseHandler databaseHandler;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initElements();
        refreshData();
        List<AirIndexStation> all = databaseHandler.getAll();
        Log.i(CLASS_TAG, "List size on activity oncreate: " + String.valueOf(all.size()));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void initElements() {
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StartFragment()).commit();

        databaseHandler = new DatabaseHandler(MainActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setCheckedItem(R.id.nav_search);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_stations:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new StationsFragment()).commit();
                        break;
                    case R.id.nav_search:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchStationFragment()).commit();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
//                if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new MessageFragment()).commit();
//            navigationView.setCheckedItem(R.id.nav_message);
//
    }


    private void refreshData() {
        Retrofit client = ApiClient.getClient();
        final StationService stationService = client.create(StationService.class);
        Call<List<Station>> listCall = stationService.allStations();
        listCall.enqueue(new Callback<List<Station>>() {
            @Override
            public void onResponse(Call<List<Station>> call, Response<List<Station>> response) {
                List<Station> body = response.body();
                for (final Station station : body) {
                    Log.i(CLASS_TAG, station.toString());
                    Call<AqIndex> aqIndexCall = stationService.getIndex(String.valueOf(station.getId()));
                    aqIndexCall.enqueue(new Callback<AqIndex>() {
                        @Override
                        public void onResponse(Call<AqIndex> call, Response<AqIndex> response) {
                            AqIndex aqIndex = response.body();
                            Log.i(CLASS_TAG, aqIndex.toString());
                            saveStation(station, aqIndex);
                        }

                        @Override
                        public void onFailure(Call<AqIndex> call, Throwable t) {
                            Log.i(CLASS_TAG, t.getMessage());

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Station>> call, Throwable t) {
                Log.i(CLASS_TAG, t.getMessage());
            }
        });
        List<String> allCities = databaseHandler.getAllCities();
        List<AirIndexStation> all = databaseHandler.getAll();
        Log.i(CLASS_TAG, "List size after update/insert: " + String.valueOf(all.size()));
    }

    private void saveStation(Station station, AqIndex aqIndex) {
        databaseHandler.saveOrUpdateStation(station, aqIndex);
    }
}