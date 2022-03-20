package com.example.peliculastest.model.network

import com.example.peliculastest.model.bean.PeliculaDetalle
import com.example.peliculastest.model.bean.PeliculasWeb
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIPeliculasInterface {

    @GET("https://api.themoviedb.org/3/movie/popular")
    fun getPeliculasPopulares(
        @Query("api_key") api_Key: String,
        @Query("language") lenguaje: String,
        @Query("page") page: Int
    ): Call<PeliculasWeb>

    @GET("https://api.themoviedb.org/3/movie/now_playing")
    fun getPeliculasPlaynow(
        @Query("api_key") api_Key: String,
        @Query("language") lenguaje: String,
        @Query("page") page: Int
    ): Call<PeliculasWeb>


}