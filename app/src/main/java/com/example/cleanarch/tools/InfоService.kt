package com.example.cleanarch.tools

import com.example.cleanarch.datalayer.Info
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface InfoService {
    @GET("/api/info")
    fun getInfo(): Call<Info>

    @GET("/api/additional-info")
    fun getAdditionalInfo(@Query("title") title: String): Call<Info>
}