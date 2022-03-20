package com.example.peliculastest.model.network;

import com.example.peliculastest.R;
import com.example.peliculastest.model.bean.PeliculasWeb;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class ApiPeliculasProvider {

    //Autoreferencia para ahcer singleton
    private static ApiPeliculasProvider apiPeliculasProvider;

    private OkHttpClient okHttpClient;
    private HttpLoggingInterceptor loggingInterceptor;
    private APIPeliculasInterface apiInterface;

    //constructor privado para singleton
    private ApiPeliculasProvider(String baseUrl,
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

        apiInterface = retrofit.create(APIPeliculasInterface.class);
    }

    public static ApiPeliculasProvider getApiPeliculasProvider(String baseUrl,
                                                           long readTimeout,
                                                           long connectTimeout,
                                                           HttpLoggingInterceptor.Level logLevel){
        if(apiPeliculasProvider==null){
            apiPeliculasProvider=new ApiPeliculasProvider(baseUrl,readTimeout,connectTimeout,logLevel);
        }
        return  apiPeliculasProvider;
    }

    public Call<PeliculasWeb> obtenerPeliculasPopulares(){
        return apiInterface.getPeliculasPopulares(DireccionesRestAPI.Companion.getApiKey(), "es-US", 1);
    }

    public Call<PeliculasWeb> obtenerPeliculasPlaynow(){
        return apiInterface.getPeliculasPlaynow(DireccionesRestAPI.Companion.getApiKey(), "es-US", 1);
    }


    /**
     * Para resolver problema de reflexion en kotlin
     */


}
