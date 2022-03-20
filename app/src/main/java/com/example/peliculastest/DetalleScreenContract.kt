package com.example.peliculastest

import com.example.peliculastest.model.bean.Pelicula
import com.example.peliculastest.model.bean.PeliculaDetalle
import com.example.peliculastest.view.vo.ParClaveValor

interface DetalleScreenContract {

    interface ViewDetalle : BaseView<PresenterDetalle> {
        fun mostrarDetalle(detalles : List<ParClaveValor>)
        fun mostrarVideo()
        fun obtenerPeliculaRecibida():Pelicula
    }

    open interface PresenterDetalle : BasePresenter {

    }

}