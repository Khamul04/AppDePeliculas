package com.example.peliculastest.model.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.peliculastest.model.bean.Pelicula
import com.example.peliculastest.model.bean.PeliculaDetalle
import java.util.*

class DetallesDatabaseAdapter {

    private val TAG: String = "DetallesAdapter"

    private val DB_NAME = "peliculastestdatabase.db"
    private val DB_VERSION = 2

    private val TABLA_PELICULAS = "tabla_detalles"
    private val COLUMNA_ID = "id"
    private val COLUMNA_BUDGET = "budget"
    private val COLUMNA_GENRES = "genres"
    private val COLUMNA_ORIGINAL_LANGUAGE = "original_language"
    private val COLUMNA_OVERVIEW = "overview"
    private val COLUMNA_RELEASE_DATE = "release_date"
    private val COLUMNA_REVENUE = "revenue"
    private val COLUMNA_RUNTIME = "runtime"
    private val COLUMNA_TAGLINE = "tagline"
    private val COLUMNA_VIDEO = "video"
    private val COLUMNA_TITLE = "title"
    private val COLUMNA_VOTE_AVERAGE = "vote_average"

    //create table table_todo(task_id integer primary key, todo text not null);
    private val CREAR_TABLA_PELICULAS =
        "CREATE TABLE " + TABLA_PELICULAS.toString() + "(" + COLUMNA_ID.toString() + " INTEGER PRIMARY KEY, " +
                COLUMNA_OVERVIEW.toString() + " TEXT, " +
                COLUMNA_TITLE.toString() + " TEXT, " +
                COLUMNA_BUDGET.toString() + " INTEGER, " +
                COLUMNA_GENRES.toString() + " TEXT, " +
                COLUMNA_VIDEO.toString() + " INTEGER, " +
                COLUMNA_REVENUE.toString() + " INTEGER, " +
                COLUMNA_RUNTIME.toString() + " INTEGER, " +
                COLUMNA_TAGLINE.toString() + " INTEGER, " +
                COLUMNA_ORIGINAL_LANGUAGE.toString() + " TEXT, " +
                COLUMNA_RELEASE_DATE.toString() + " TEXT, " +
                COLUMNA_VOTE_AVERAGE+ " REAL )"

    private var context: Context? = null
    private var sqLliteDatabase: SQLiteDatabase? = null


    constructor(context: Context?){
        this.context=context
        sqLliteDatabase= PeliculasDBHelper(this.context, DB_NAME, null, DB_VERSION).getWritableDatabase()
    }

    fun insert(peliculaItem: PeliculaDetalle): Boolean {
        val contentValues = ContentValues()
        contentValues.put(COLUMNA_ID, peliculaItem.id)
        contentValues.put(COLUMNA_TITLE, peliculaItem.title)
        contentValues.put(COLUMNA_BUDGET, peliculaItem.budget)
        contentValues.put(COLUMNA_OVERVIEW, peliculaItem.overview)
        contentValues.put(COLUMNA_VIDEO, if(peliculaItem.video==true)1 else 0)
        contentValues.put(COLUMNA_GENRES, peliculaItem.genres[0].name)
        contentValues.put(COLUMNA_RELEASE_DATE, peliculaItem.release_date)
        contentValues.put(COLUMNA_REVENUE, peliculaItem.revenue)
        contentValues.put(COLUMNA_TAGLINE, peliculaItem.tagline)
        contentValues.put(COLUMNA_VOTE_AVERAGE, peliculaItem.vote_average)
        contentValues.put(COLUMNA_ORIGINAL_LANGUAGE, peliculaItem.original_language)
        contentValues.put(COLUMNA_RUNTIME, peliculaItem.runtime)

        var retorno =  sqLliteDatabase?.insert(TABLA_PELICULAS, null, contentValues)
        return !(retorno == null || retorno<0)
    }

    fun delete(idDetalle: Long): Boolean {
        var retorno =  sqLliteDatabase?.delete(
            TABLA_PELICULAS,
            COLUMNA_ID + " = " + idDetalle,
            null
        )
        return !(retorno == null || retorno<0)
    }

    //Helper para peliculas
    private inner class PeliculasDBHelper(
        context: Context?,
        databaseName: String?,
        factory: SQLiteDatabase.CursorFactory?,
        dbVersion: Int
    ) :
        SQLiteOpenHelper(context, databaseName, factory, dbVersion) {

        override fun onConfigure(db: SQLiteDatabase) {
            super.onConfigure(db)
            db.setForeignKeyConstraintsEnabled(true)
            Log.i(TAG, "Inside onConfigure")
        }

        override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
            sqLiteDatabase.execSQL(CREAR_TABLA_PELICULAS)
            Log.i(TAG, "Inside onCreate")
        }

        override fun onUpgrade(
            sqLiteDatabase: SQLiteDatabase,
            oldVersion: Int, newVersion: Int
        ) {
            //no implementado
        }
    }

}