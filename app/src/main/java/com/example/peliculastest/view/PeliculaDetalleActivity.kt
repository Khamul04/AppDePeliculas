package com.example.peliculastest.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.peliculastest.DetalleScreenContract
import com.example.peliculastest.R
import com.example.peliculastest.model.bean.Pelicula
import com.example.peliculastest.presenter.DetalleScreenPresenterImpl
import com.example.peliculastest.view.adapters.DetallesRecyclerAdapter
import com.example.peliculastest.view.vo.ParClaveValor


class PeliculaDetalleActivity : AppCompatActivity(), DetalleScreenContract.ViewDetalle{

    private lateinit var presenter: DetalleScreenContract.PresenterDetalle

    private var recyclerViewDetalles: RecyclerView? = null
    private var detallesAdapter : DetallesRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pelicula_detalle)
        this.presenter = DetalleScreenPresenterImpl(this);

    }

    override fun onResume() {
        super.onResume()
        presenter.start(this)
    }

    override fun obtenerPeliculaRecibida(): Pelicula {
        val intent = this.intent
        val bundle = intent.extras
        var pelicula = bundle?.getSerializable("pelicula")
        if(pelicula == null)
            pelicula = Pelicula()
        return pelicula as Pelicula
    }

    override fun mostrarDetalle(detalles: List<ParClaveValor>) {
        val linearLayoutManagerPopulares = LinearLayoutManager(this)
        linearLayoutManagerPopulares.setOrientation(LinearLayoutManager.VERTICAL)
        recyclerViewDetalles = findViewById<View>(R.id.recyclerViewDetalles) as RecyclerView
        recyclerViewDetalles?.setLayoutManager(linearLayoutManagerPopulares)

        detallesAdapter = DetallesRecyclerAdapter(detalles)
        recyclerViewDetalles?.setAdapter(detallesAdapter)
    }

    override fun mostrarVideo() {
        //no implementado
    }

    override fun setPresenter(presenter: DetalleScreenContract.PresenterDetalle) {
        this.presenter = presenter
    }
}