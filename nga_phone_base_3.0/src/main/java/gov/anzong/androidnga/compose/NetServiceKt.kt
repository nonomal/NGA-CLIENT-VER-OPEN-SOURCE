package gov.anzong.androidnga.compose

import retrofit2.http.GET
import retrofit2.http.QueryMap

interface NetServiceKt {

    @GET("nuke.php")
    suspend fun getString(@QueryMap map: Map<String, String>): String
}