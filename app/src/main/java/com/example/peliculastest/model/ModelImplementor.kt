package com.example.peliculastest.model

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.peliculastest.model.bean.Pelicula
import com.example.peliculastest.model.bean.PeliculaDetalle
import com.example.peliculastest.model.bean.PeliculasWeb
import com.example.peliculastest.model.database.PeliculasPlaynowDatabaseAdapter
import com.example.peliculastest.model.database.PeliculasPopularesDatabaseAdapter
import com.example.peliculastest.model.network.ApiPeliculaDetalleProvider
import com.example.peliculastest.model.network.ApiPeliculasProvider
import retrofit2.Call
import java.util.*

class ModelImplementor : Model {

    var peliculasPopularesDatabaseAdapter: PeliculasPopularesDatabaseAdapter? = null
    var peliculasPlaynowAdapter: PeliculasPlaynowDatabaseAdapter? = null
    var peliculasWebProvider: ApiPeliculasProvider? = null
    var peliculaDetalleWebProvider: ApiPeliculaDetalleProvider? = null

    var peliculasPopularesList: List<Pelicula>? = ArrayList<Pelicula>()
    var peliculasPlaynowList: List<Pelicula>? = ArrayList<Pelicula>()

    constructor(){

    }

    constructor(peliculasPopularesAdapter: PeliculasPopularesDatabaseAdapter?, peliculasPlaynowAdapter: PeliculasPlaynowDatabaseAdapter?, peliculasWebProvider: ApiPeliculasProvider?, peliculaDetalleWebProvider: ApiPeliculaDetalleProvider?){
        this.peliculasPopularesDatabaseAdapter = peliculasPopularesAdapter
        this.peliculasPlaynowAdapter = peliculasPlaynowAdapter
        this.peliculaDetalleWebProvider = peliculaDetalleWebProvider

        this.peliculasWebProvider = peliculasWebProvider
    }

    override fun getDetalleFromWeb(pelicula : Pelicula): Call<PeliculaDetalle>? {
        return this.peliculaDetalleWebProvider?.obtenerPeliculasPopulares(pelicula.id)
    }

    override fun solicitarTodosLosDetallesDeLasPeliculasEnLaBaseDeDatos(): Boolean? {
        //obtener todos los registros de la tabla popular y playnow
        var ids_peliculas = this.peliculasPlaynowAdapter?.obtenerTodosLosRegistrosDePlaynow()
        //iterar registro por registro
        val listaDetalles = ArrayList<PeliculaDetalle>()
        if(ids_peliculas!=null) {
            for (i in ids_peliculas) {
                val response = this.peliculaDetalleWebProvider?.obtenerPeliculasPopulares(i)?.execute()
                listaDetalles.add(response!!.body())
            }
            this.guardarDetalles(listaDetalles)
        }else return false

        return true

    }

    override fun getMostPopularMoviesFromWeb(): Call<PeliculasWeb>? {
        return this.peliculasWebProvider?.obtenerPeliculasPopulares()
    }

    override fun getMostPlayNowMoviesFromWeb(): Call<PeliculasWeb>? {
        return this.peliculasWebProvider?.obtenerPeliculasPlaynow()
    }

    override fun getMostPopularMoviesFromLocal(): List<Pelicula>? {
        this.peliculasPopularesList = this.peliculasPopularesDatabaseAdapter?.getPeliculasPopulares();
        if(this.peliculasPopularesList!=null && this.peliculasPopularesList?.size?:0 >0){
            return this.peliculasPopularesList;
        } else {
            //regresamos lista vacia
            return ArrayList<Pelicula>()
        }
    }

    override fun getMostPlayNowMoviesFromLocal(): List<Pelicula>? {
        this.peliculasPlaynowList = this.peliculasPlaynowAdapter?.getPeliculasPlaynow();
        if(this.peliculasPlaynowList!=null && this.peliculasPlaynowList?.size?:0 >0){
            return this.peliculasPlaynowList;
        } else {
            //regresamos lista vacia
            return ArrayList<Pelicula>()
        }
    }

    override fun getDetalleFromLocal(p: Pelicula): String {
        //falta implemetar
       return ""
    }

    override fun guardarListadePeliculasPopulares(p: List<Pelicula>): Boolean? {
        return this.peliculasPopularesDatabaseAdapter?.guardarNuevaLista(p)
    }

    override fun guardarListadePeliculasPlaynow(p: List<Pelicula>): Boolean? {
        return this.peliculasPlaynowAdapter?.guardarNuevaLista(p)
    }

    override fun guardarDetalles(detalle: List<PeliculaDetalle>): Boolean? {
        //Guardara esta lista de detalles en la base de datos
        return false
    }

    override fun actualizarUltimaFechaDeDescarga(context: Context) {
        val sp = context.getSharedPreferences("ultimaDescarga", Context.MODE_PRIVATE)
        val editor = sp.edit();
        editor.putLong("fechaUltimaDescarga",System.currentTimeMillis());
        editor.apply();
    }

    override fun esNecesarioDescargar(context: Context): Boolean {
        val sp = context.getSharedPreferences("ultimaDescarga", Context.MODE_PRIVATE)
        val ultimaDescarga = sp.getLong("fechaUltimaDescarga", -1)
        val time = System.currentTimeMillis()
        //refresca cada 6hr (la api menciona que las listas se actualizan 1 vez al dia)
        if((time-ultimaDescarga)>=21600000 || ultimaDescarga<0) {
            return true
        }
        return false
    }


}