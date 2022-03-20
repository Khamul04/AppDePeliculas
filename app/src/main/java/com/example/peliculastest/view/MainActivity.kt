package com.example.peliculastest.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.peliculastest.MainScreenContract
import com.example.peliculastest.R
import com.example.peliculastest.model.bean.Pelicula
import com.example.peliculastest.presenter.MainScreenPresenterImpl
import com.example.peliculastest.view.adapters.MoviesRecyclerAdapter
import com.example.peliculastest.view.vo.Fix

class MainActivity : AppCompatActivity(), MainScreenContract.View, MoviesRecyclerAdapter.ListItemClickListener {

    private lateinit var presenter: MainScreenContract.Presenter
    private var recyclerViewPeliculasPopulares: RecyclerView? = null
    private var recyclerViewPeliculasPlaynow: RecyclerView? = null
    var peliculasPopularesAdapter : MoviesRecyclerAdapter? = null
    var peliculasPlaynowAdapter : MoviesRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.presenter = MainScreenPresenterImpl(this);


    }

    override fun onResume() {
        super.onResume()
        presenter.start(this)
    }

    override fun showPopularMovies(peliculasPopulares: List<Pelicula>) {
        val linearLayoutManagerPopulares = LinearLayoutManager(this)
        linearLayoutManagerPopulares.setOrientation(LinearLayoutManager.HORIZONTAL)
        recyclerViewPeliculasPopulares = findViewById<View>(R.id.peliculasPopulares) as RecyclerView
        recyclerViewPeliculasPopulares?.setLayoutManager(linearLayoutManagerPopulares)

        peliculasPopularesAdapter = MoviesRecyclerAdapter(this, peliculasPopulares, this)
        recyclerViewPeliculasPopulares?.setAdapter(peliculasPopularesAdapter)
    }

    override fun showPlaynowMovies(peliculasPlaynow: List<Pelicula>) {
        val linearLayoutManagerPlaynow = LinearLayoutManager(this)
        linearLayoutManagerPlaynow.setOrientation(LinearLayoutManager.HORIZONTAL)
        recyclerViewPeliculasPlaynow = findViewById<View>(R.id.peliculasPlaynow) as RecyclerView
        recyclerViewPeliculasPlaynow?.setLayoutManager(linearLayoutManagerPlaynow)

        peliculasPlaynowAdapter = MoviesRecyclerAdapter(this, peliculasPlaynow, this)
        recyclerViewPeliculasPlaynow?.setAdapter(peliculasPlaynowAdapter)
    }

    override fun showError(errorMessage: String?) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    override fun navigateToDetailActivity(pelicula :Pelicula) {
        //Deberia borrarse este metodo

    }

    override fun setPresenter(presenter: MainScreenContract.Presenter) {
        this.presenter = presenter;
    }

    override fun onItemClicked(pelicula : Pelicula) {
        this.presenter.onMovieItemSelected(this, pelicula)
    }
}