package com.example.peliculastest.model.network;

import android.util.Log;

import com.example.peliculastest.model.bean.PeliculaDetalle;
import com.example.peliculastest.model.bean.PeliculasWeb;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiPeliculaDetalleProvider {

    //Autoreferencia para ahcer singleton
    private static ApiPeliculaDetalleProvider apiPeliculaDetalleProvider;

    private OkHttpClient okHttpClient;
    private HttpLoggingInterceptor loggingInterceptor;
    private ApiPeliculaDetalleInterface apiInterface;

    //constructor privado para singleton
    private ApiPeliculaDetalleProvider(
                                 long readTimeout,
                                 long connectTimeout,
                                 HttpLoggingInterceptor.Level logLevel){
        loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(logLevel);

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
                .connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(DireccionesRestAPI.Companion.getBaseRemoteHostUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();

        apiInterface = retrofit.create(ApiPeliculaDetalleInterface.class);
    }

    public static ApiPeliculaDetalleProvider getApiPeliculaDetalleProvider(
                                                               long readTimeout,
                                                               long connectTimeout,
                                                               HttpLoggingInterceptor.Level logLevel){
        if(apiPeliculaDetalleProvider==null){
            apiPeliculaDetalleProvider=new ApiPeliculaDetalleProvider(readTimeout,connectTimeout,logLevel);
        }
        return  apiPeliculaDetalleProvider;
    }

    public Call<PeliculaDetalle> obtenerPeliculasPopulares(Long movieId){
        return apiInterface.getPeliculaDetalle(movieId.toString(), DireccionesRestAPI.Companion.getApiKey(), "es-US");
    }


}
