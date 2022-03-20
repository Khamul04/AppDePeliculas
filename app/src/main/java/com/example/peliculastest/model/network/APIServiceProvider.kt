package com.example.peliculastest.model.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Converter.Factory
import java.util.concurrent.TimeUnit
import com.fasterxml.jackson.*
import com.fasterxml.jackson.annotation.JacksonInject
import retrofit2.converter.jackson.JacksonConverterFactory
import kotlin.reflect.KClass


class APIServiceProvider {

    //Autoreferencia para ahcer singleton
    private val apiServiceProvider: APIServiceProvider? = null

    private var okHttpClient: OkHttpClient? = null
    private var loggingInterceptor: HttpLoggingInterceptor? = null
    var apiInterface: APIPeliculasInterface? = null

    private fun APIServiceProvider(
        baseUrl: String,
        readTimeout: Long,
        connectTimeout: Long,
        logLevel: HttpLoggingInterceptor.Level
    ) {
        loggingInterceptor = HttpLoggingInterceptor()
        (loggingInterceptor as HttpLoggingInterceptor).setLevel(logLevel)
        okHttpClient = OkHttpClient.Builder()
            .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
            .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(loggingInterceptor)
            .build()
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(DireccionesRestAPI.peliculasPopulares)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build()
        //apiInterface = retrofit.create(APIPeliculasInterface::class.java)
        apiInterface = retrofit.create(APIPeliculasInterface::class as Class<APIPeliculasInterface>)
    }

}