package com.example.peliculastest.presenter

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import com.bumptech.glide.Glide
import com.example.peliculastest.ApplicationMovies
import com.example.peliculastest.MainScreenContract
import com.example.peliculastest.model.Model
import com.example.peliculastest.model.bean.Pelicula
import com.example.peliculastest.model.bean.PeliculasWeb
import com.example.peliculastest.view.vo.Fix
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainScreenPresenterImpl : MainScreenContract.Presenter {

    var view: MainScreenContract.View?
    var model: Model?

    constructor(view: MainScreenContract.View){
        this.view = view
        this.model = ApplicationMovies.obtenerModel()

        this.view?.setPresenter(this)
    }

    override fun onMovieItemSelected(context : Context, pelicula: Pelicula) {
        //implementar detalle
        //agregar despues: si hay que descargar o si sale de local
        //this.model?.getDetalleFromWeb(pelicula)
        Fix.intent(context, pelicula)
    }

    override fun start(context : Context) {
        /*Si es encesario traer datos de internet los traemos
            sino, los sacamos de la base de datos
         */
        if(this.model?.esNecesarioDescargar(context)!!){
            var callPeliculasPopulares = model?.getMostPopularMoviesFromWeb()
            callPeliculasPopulares?.enqueue(object : Callback<PeliculasWeb> {
                override fun onResponse(call: Call<PeliculasWeb>?, response: Response<PeliculasWeb>?) {
                    //sacar el body del response armar una lista de pelis local
                    var resultado = ArrayList<Pelicula>()
                    if (response == null || response.body() == null || response.body().totalResults <= 0) {
                        resultado = ArrayList<Pelicula>()
                    } else {
                        resultado = response.body().results
                        //mandar la lista al view
                        view?.showPopularMovies(resultado)
                        //mandar la lista a la base de datos local
                        model?.guardarListadePeliculasPopulares(resultado)
                        model?.actualizarUltimaFechaDeDescarga(context)
                    }
                }

                override fun onFailure(call: Call<PeliculasWeb>?, t: Throwable?) {
                    var resultado = ArrayList<Pelicula>()
                    view?.showPopularMovies(resultado)
                }

            })

            var callPeliculasPlaynow = model?.getMostPlayNowMoviesFromWeb()
            callPeliculasPlaynow?.enqueue(object : Callback<PeliculasWeb> {
                override fun onResponse(call: Call<PeliculasWeb>?, response: Response<PeliculasWeb>?) {
                    //sacar el body del response armar una lista de pelis local
                    var resultado = ArrayList<Pelicula>()
                    if (response == null || response.body() == null || response.body().totalResults <= 0) {
                        resultado = ArrayList<Pelicula>()
                    } else {
                        resultado = response.body().results
                        //mandar la lista al view
                        view?.showPlaynowMovies(resultado)
                        //mandar la lista a la base de datos local
                        model?.guardarListadePeliculasPlaynow(resultado)
                        model?.actualizarUltimaFechaDeDescarga(context)
                    }

                }

                override fun onFailure(call: Call<PeliculasWeb>?, t: Throwable?) {
                    var resultado = ArrayList<Pelicula>()
                    view?.showPlaynowMovies(resultado)
                }

            })
        }else{
            val peliculasPopulares = model?.getMostPopularMoviesFromLocal()!!
            view?.showPopularMovies(peliculasPopulares)
            val peliculasPlaynow = model?.getMostPlayNowMoviesFromLocal()!!
            view?.showPlaynowMovies(peliculasPlaynow)

            this.model?.actualizarUltimaFechaDeDescarga(context)

        }



    }

}