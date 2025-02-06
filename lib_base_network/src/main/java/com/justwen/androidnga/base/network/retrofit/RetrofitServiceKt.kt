package com.justwen.androidnga.base.network.retrofit

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RetrofitServiceKt {

    @GET("nuke.php")
    suspend fun getString(@QueryMap map: Map<String, String>): String
}