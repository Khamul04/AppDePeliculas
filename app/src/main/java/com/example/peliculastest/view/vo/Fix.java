package com.example.peliculastest.view.vo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.peliculastest.model.bean.Pelicula;
import com.example.peliculastest.view.PeliculaDetalleActivity;

public class Fix {

    public static void intent(Context context, Pelicula pelicula){

        Intent intent = new Intent(context, PeliculaDetalleActivity.class);
        intent.putExtra("pelicula", pelicula);
        context.startActivity(intent);

    }

}
