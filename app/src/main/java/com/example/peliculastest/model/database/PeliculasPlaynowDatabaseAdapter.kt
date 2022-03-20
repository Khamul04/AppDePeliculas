package com.example.peliculastest.model.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.peliculastest.model.bean.Pelicula
import java.util.*

class PeliculasPlaynowDatabaseAdapter {

    private val TAG: String = "PeliculasAdapter"

    private val DB_NAME = "peliculastestdatabase.db"
    private val DB_VERSION = 2

    private val TABLA_PELICULAS = "tabla_peliculas_playnow"
    private val COLUMNA_ID = "id"
    private val COLUMNA_OVERVIEW = "overview"
    private val COLUMNA_TITLE = "title"
    private val COLUMNA_ORIGINAL_TITLE = "originaltitle"
    private val COLUMNA_VIDEO = "video"
    private val COLUMNA_POSTER_PATH = "posterpath"
    private val COLUMNA_POPULAR = "espopular"
    private val COLUMNA_PLAY_NOW = "esplaynow"

    //create table table_todo(task_id integer primary key, todo text not null);
    private val CREAR_TABLA_PELICULAS =
        "CREATE TABLE " + TABLA_PELICULAS.toString() + "(" + COLUMNA_ID.toString() + " INTEGER PRIMARY KEY, " +
                COLUMNA_OVERVIEW.toString() + " TEXT, " +
                COLUMNA_TITLE.toString() + " TEXT, " +
                COLUMNA_ORIGINAL_TITLE.toString() + " TEXT, " +
                COLUMNA_VIDEO.toString() + " INTEGER, " +
                COLUMNA_POSTER_PATH.toString() + " TEXT, " +
                COLUMNA_POPULAR.toString() + " INTEGER, " +
                COLUMNA_PLAY_NOW + " INTEGER )"

    private var context: Context? = null
    private var sqLliteDatabase: SQLiteDatabase? = null

    companion object {
        private var peliculasAdapterInstance: PeliculasPlaynowDatabaseAdapter? = null

        fun obtenerPeliculasDBAdapterInstance(context: Context?): PeliculasPlaynowDatabaseAdapter? {
            if (peliculasAdapterInstance == null) {
                peliculasAdapterInstance = PeliculasPlaynowDatabaseAdapter(context)
            }
            return peliculasAdapterInstance
        }
    }

    constructor(context: Context?){
        this.context=context
        sqLliteDatabase= PeliculasDBHelper(this.context, DB_NAME, null, DB_VERSION).getWritableDatabase()
    }

    fun insert(peliculaItem: Pelicula): Boolean {
        val contentValues = ContentValues()
        contentValues.put(COLUMNA_ID, peliculaItem.id)
        contentValues.put(COLUMNA_TITLE, peliculaItem.titulo)
        contentValues.put(COLUMNA_ORIGINAL_TITLE, peliculaItem.tituloOriginal)
        contentValues.put(COLUMNA_OVERVIEW, peliculaItem.overview)
        contentValues.put(COLUMNA_VIDEO, if(peliculaItem.video==true)1 else 0)
        contentValues.put(COLUMNA_POSTER_PATH, peliculaItem.posterPath)
        contentValues.put(COLUMNA_POPULAR, peliculaItem.esPopular)
        contentValues.put(COLUMNA_PLAY_NOW, peliculaItem.esPlayNow)

        var retorno =  sqLliteDatabase?.insert(TABLA_PELICULAS, null, contentValues)
        return !(retorno == null || retorno<0)
    }

    fun delete(idPelicula: Long): Boolean {
        var retorno =  sqLliteDatabase?.delete(
            TABLA_PELICULAS,
            COLUMNA_ID + " = " + idPelicula,
            null
        )
        return !(retorno == null || retorno<0)
    }

    fun obtenerTodosLosRegistrosDePlaynow(): List<Long> {
        val peliculasList: MutableList<Long> = ArrayList<Long>()
        sqLliteDatabase?.execSQL("SELECT * FROM tabla_peliculas_playnow");

        val columna = arrayOf("id")
        val cursor: Cursor = sqLliteDatabase!!.query(
            TABLA_PELICULAS,
            columna,
            null,
            null,
            null,
            null,
            null,
            null
        )
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                var id = cursor.getLong(0)
                peliculasList.add(id)
            }
        }
        cursor.close()
        return peliculasList
    }

    fun guardarNuevaLista(lista:List<Pelicula>): Boolean {
        sqLliteDatabase?.execSQL("DROP TABLE IF EXISTS "+TABLA_PELICULAS);
        sqLliteDatabase?.execSQL(CREAR_TABLA_PELICULAS);

        for(pelicula in lista){
            if(!this.insert(pelicula))
                return false
        }
        return true
    }

    fun update(idPelicula: Long, peliculaItem: Pelicula): Boolean {
        val contentValues = ContentValues()
        contentValues.put(COLUMNA_ID, peliculaItem.id)
        contentValues.put(COLUMNA_TITLE, peliculaItem.titulo)
        contentValues.put(COLUMNA_ORIGINAL_TITLE, peliculaItem.tituloOriginal)
        contentValues.put(COLUMNA_OVERVIEW, peliculaItem.overview)
        contentValues.put(COLUMNA_VIDEO, if(peliculaItem.video==true)1 else 0)
        contentValues.put(COLUMNA_POSTER_PATH, peliculaItem.posterPath)
        contentValues.put(COLUMNA_POPULAR, peliculaItem.esPopular)
        contentValues.put(COLUMNA_PLAY_NOW, peliculaItem.esPlayNow)

        val retorno = sqLliteDatabase?.update(
            TABLA_PELICULAS,
            contentValues,
            COLUMNA_ID.toString() + " = " + idPelicula,
            null
        )

        return !(retorno == null || retorno<0)
    }

    fun getPeliculasPopulares(): List<Pelicula>? {
        val peliculasList: MutableList<Pelicula> = ArrayList<Pelicula>()
        val cursor: Cursor = sqLliteDatabase!!.query(
            TABLA_PELICULAS,
            null,
            "espopular = 1",
            null,
            null,
            null,
            null,
            null
        )
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                val pel = Pelicula()
                pel.id = cursor.getLong(0)
                pel.overview = cursor.getString(1)
                pel.titulo = cursor.getString(2)
                pel.tituloOriginal = cursor.getString(3)
                pel.video = if(cursor.getInt(4)==1)true else false
                pel.posterPath = cursor.getString(5)
                pel.esPopular = cursor.getInt(6)
                pel.esPlayNow = cursor.getInt(7)
                peliculasList.add(pel)
            }
        }
        cursor.close()
        return peliculasList
    }

    fun getPeliculasPlaynow(): List<Pelicula>? {
        val peliculasList: MutableList<Pelicula> = ArrayList<Pelicula>()
        val cursor: Cursor = sqLliteDatabase!!.query(
            TABLA_PELICULAS,
            null,
            "esplaynow = 1",
            null,
            null,
            null,
            null,
            null
        )
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                val pel = Pelicula()
                pel.id = cursor.getLong(0)
                pel.overview = cursor.getString(1)
                pel.titulo = cursor.getString(2)
                pel.tituloOriginal = cursor.getString(3)
                pel.video = if(cursor.getInt(4)==1)true else false
                pel.posterPath = cursor.getString(5)
                pel.esPopular = cursor.getInt(6)
                pel.esPlayNow = cursor.getInt(7)
                peliculasList.add(pel)
            }
        }
        cursor.close()
        return peliculasList
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