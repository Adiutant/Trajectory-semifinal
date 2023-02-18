package com.study.trajectory_semifinal.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import com.study.trajectory_semifinal.model.Data;

public interface JsonAPI {

    @GET("/semi-final-data.json")
    Call<Data> findAll();
}
