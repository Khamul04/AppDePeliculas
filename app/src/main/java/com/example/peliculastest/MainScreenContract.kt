package com.example.peliculastest

import android.content.Context
import com.example.peliculastest.model.bean.Pelicula

interface MainScreenContract {

    interface View : BaseView<Presenter> {
        fun showPopularMovies(peliculasPopulares: List<Pelicula>)
        fun showPlaynowMovies(peliculasPlaynow: List<Pelicula>)
        fun showError(errorMessage: String?)
        fun navigateToDetailActivity(pelicula: Pelicula)
    }

    open interface Presenter : BasePresenter {
        fun onMovieItemSelected(context : Context, pelicula : Pelicula)
    }

}