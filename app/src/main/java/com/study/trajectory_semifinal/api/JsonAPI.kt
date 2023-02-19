package com.study.trajectory_semifinal.api

import com.study.trajectory_semifinal.model.Data
import retrofit2.Call
import retrofit2.http.GET

interface JsonAPI {
    @GET("/semi-final-data.json")
    fun findAll(): Call<Data?>?
}