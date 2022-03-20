package com.example.peliculastest.model.network

import com.example.peliculastest.model.bean.PeliculaDetalle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiPeliculaDetalleInterface {

    @GET("https://api.themoviedb.org/3/movie/"+"{movie_id}")
    fun getPeliculaDetalle(
        @Path(value = "movie_id") movieId:String,
        @Query("api_key") api_Key: String,
        @Query("language") lenguaje: String
    ): Call<PeliculaDetalle>

}