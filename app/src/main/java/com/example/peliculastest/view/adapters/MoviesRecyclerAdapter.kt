package com.example.peliculastest.view.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.peliculastest.R
import com.example.peliculastest.model.bean.Pelicula


//usado para ambas listas de peliculas
class MoviesRecyclerAdapter(val context: Context, val listaPeliculas: List<Pelicula>, val oyente :ListItemClickListener) : RecyclerView.Adapter<MoviesRecyclerAdapter.MovieViewHolder>() {

    interface ListItemClickListener {
        fun onItemClicked(pelicula : Pelicula)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        val movieViewHolder = MovieViewHolder(layoutInflater)

        return movieViewHolder
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val pelicula:Pelicula = this.listaPeliculas.get(position)
        holder.card.setOnClickListener {
            this.listaPeliculas.get(position)
            this.oyente.onItemClicked(pelicula)
        }
        holder.bind(context, pelicula)
    }

    override fun getItemCount(): Int {
        return this.listaPeliculas.size
    }

    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val titulo = view.findViewById<TextView>(R.id.titulo)
        private val image = view.findViewById<ImageView>(R.id.imageView)
        val card = view.findViewById<CardView>(R.id.cardPelicula)

        fun bind(context: Context, pelicula: Pelicula){
            titulo.text = pelicula.titulo
            Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + pelicula.posterPath).into(
                image
            )
        }
    }

}