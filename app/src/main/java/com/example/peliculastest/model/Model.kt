package com.example.peliculastest.model

import android.content.Context
import com.example.peliculastest.model.bean.Pelicula
import com.example.peliculastest.model.bean.PeliculaDetalle
import com.example.peliculastest.model.bean.PeliculasWeb
import retrofit2.Call

interface Model {

    //Fuentes remotas en webservice
    fun getMostPopularMoviesFromWeb(): Call<PeliculasWeb>?

    fun getMostPlayNowMoviesFromWeb(): Call<PeliculasWeb>?

    fun getDetalleFromWeb(pelicula:Pelicula): Call<PeliculaDetalle>?

    fun solicitarTodosLosDetallesDeLasPeliculasEnLaBaseDeDatos(): Boolean?

    //fuentes locales en database
    fun getMostPopularMoviesFromLocal(): List<Pelicula>?

    fun getMostPlayNowMoviesFromLocal(): List<Pelicula>?

    fun getDetalleFromLocal(p:Pelicula): String

    fun guardarListadePeliculasPopulares(p:List<Pelicula>): Boolean?

    fun guardarListadePeliculasPlaynow(p:List<Pelicula>): Boolean?

    fun guardarDetalles(detalle:List<PeliculaDetalle>): Boolean?

    //fuentes locales de shared preferences
    fun actualizarUltimaFechaDeDescarga(context:Context)

    fun esNecesarioDescargar(context:Context): Boolean



}