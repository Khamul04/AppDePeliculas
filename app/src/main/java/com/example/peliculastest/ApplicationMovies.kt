package com.example.peliculastest

import android.app.Application
import android.util.Log
import com.example.peliculastest.model.Model
import com.example.peliculastest.model.ModelImplementor
import com.example.peliculastest.model.database.PeliculasPlaynowDatabaseAdapter
import com.example.peliculastest.model.database.PeliculasPopularesDatabaseAdapter
import com.example.peliculastest.model.network.ApiPeliculaDetalleProvider
import com.example.peliculastest.model.network.ApiPeliculasProvider
import com.example.peliculastest.model.network.DireccionesRestAPI
import okhttp3.logging.HttpLoggingInterceptor


class ApplicationMovies: Application() {

    companion object {
        private var peliculasPopularesAdapter: PeliculasPopularesDatabaseAdapter? = null
        private var peliculasPlaynowAdapter: PeliculasPlaynowDatabaseAdapter? = null
        private var peliculasWebProvider: ApiPeliculasProvider? = null
        private var detallePeliculaWebProvider: ApiPeliculaDetalleProvider? = null
        private var model: Model? = null

        fun obtenerPeliculasPopularesAdapter(): PeliculasPopularesDatabaseAdapter? {
            return peliculasPopularesAdapter
        }

        fun obtenerModel(): Model? {
            return model
        }

        fun obtenerPeliculasPlaynowAdapter(): PeliculasPlaynowDatabaseAdapter? {
            return peliculasPlaynowAdapter
        }
    }

    override fun onCreate() {
        super.onCreate()
        peliculasPopularesAdapter = PeliculasPopularesDatabaseAdapter.obtenerPeliculasDBAdapterInstance(this)
        peliculasPlaynowAdapter =   PeliculasPlaynowDatabaseAdapter.obtenerPeliculasDBAdapterInstance(this)
        peliculasWebProvider = ApiPeliculasProvider.getApiPeliculasProvider(DireccionesRestAPI.baseRemoteHostUrl, 30000,30000, HttpLoggingInterceptor.Level.BODY)
        detallePeliculaWebProvider = ApiPeliculaDetalleProvider.getApiPeliculaDetalleProvider( 30000, 30000, HttpLoggingInterceptor.Level.BODY)
        model = ModelImplementor(peliculasPopularesAdapter, peliculasPlaynowAdapter, peliculasWebProvider, detallePeliculaWebProvider)
    }

}