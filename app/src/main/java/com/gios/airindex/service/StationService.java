package com.gios.airindex.service;

import com.gios.airindex.model.AqIndex;
import com.gios.airindex.model.Station;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StationService {


    @GET("station/findAll")
    Call<List<Station>> allStations();

    @GET("aqindex/getIndex/{id}")
    Call<AqIndex> getIndex(@Path("id") String id);


}
