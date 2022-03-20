package com.example.peliculastest.presenter

import android.content.Context
import android.util.Log
import com.example.peliculastest.ApplicationMovies
import com.example.peliculastest.DetalleScreenContract
import com.example.peliculastest.MainScreenContract
import com.example.peliculastest.model.Model
import com.example.peliculastest.model.bean.*
import com.example.peliculastest.view.vo.ParClaveValor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DetalleScreenPresenterImpl : DetalleScreenContract.PresenterDetalle {

    var view: DetalleScreenContract.ViewDetalle?
    var model: Model?

    constructor(view: DetalleScreenContract.ViewDetalle){
        this.view = view
        this.model = ApplicationMovies.obtenerModel()

        this.view?.setPresenter(this)
    }

    override fun start(context: Context) {
        /*TODO("Not yet implemented")
        Aqui debes llamar el servicio que traera el detalle
        aqui debes convertir el objeto Detallepelicula en una lista claveValor*/

        val pelicula = this.view?.obtenerPeliculaRecibida()
        this.model?.getDetalleFromWeb(pelicula!!)?.enqueue(object : Callback<PeliculaDetalle> {
            override fun onResponse(
                call: Call<PeliculaDetalle>?,
                response: Response<PeliculaDetalle>?
            ) {
                val resultado = if (response == null || response.body() == null) {
                    PeliculaDetalle()
                } else {
                    response.body()
                }
                val lista = conviertirPeliculaDetalleEnListParClaveValor(resultado)

                view?.mostrarDetalle(lista)
                //mandar la lista a la base de datos local
                //model?
                //para guardar todos lso detalles debe hacerse al consultar cada pelicula
                //model?.actualizarUltimaFechaDeDescarga(context)
            }

            fun conviertirPeliculaDetalleEnListParClaveValor(peliculaDetalle: PeliculaDetalle?):List<ParClaveValor>{
                val listResultado = ArrayList<ParClaveValor>()
                val titulo = ParClaveValor("Titulo:", peliculaDetalle!!.title)
                val frase = ParClaveValor("Frase:", peliculaDetalle.tagline)
                val sinopsis = ParClaveValor("Sinopsis:", peliculaDetalle.overview)
                val costo = ParClaveValor("Costo:", "$"+peliculaDetalle.budget)
                val revenue = ParClaveValor("Recaudación:", "$"+peliculaDetalle.revenue)
                val idioma = ParClaveValor("Idioma:", peliculaDetalle.original_language)
                val critica = ParClaveValor("Critica:", ""+peliculaDetalle.vote_average)
                val fechaSalida = ParClaveValor("Estreno:", ""+peliculaDetalle.release_date)
                val duracion = ParClaveValor("Duración:", ""+peliculaDetalle.runtime+"min")
                val generosCadena = ""
                for(i in peliculaDetalle.genres){
                    generosCadena.plus(", "+i.name)
                }
                val generos = ParClaveValor("Generos: ", generosCadena)

                listResultado.add(titulo)
                listResultado.add(frase)
                listResultado.add(sinopsis)
                listResultado.add(generos)
                listResultado.add(costo)
                listResultado.add(revenue)
                listResultado.add(idioma)
                listResultado.add(critica)
                listResultado.add(fechaSalida)
                listResultado.add(duracion)
                return listResultado
            }

            override fun onFailure(call: Call<PeliculaDetalle>?, t: Throwable?) {
                var resultado = ArrayList<ParClaveValor>()
                Log.e("Armando", ""+t?.message);
                view?.mostrarDetalle(resultado)
            }


        })
    }
}