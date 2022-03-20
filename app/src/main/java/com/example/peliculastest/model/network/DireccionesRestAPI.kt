package com.example.peliculastest.model.network

interface DireccionesRestAPI {

    companion object {
        var baseRemoteHostUrl = "https://api.themoviedb.org/3/"
        var peliculasPopulares = baseRemoteHostUrl+"movie/popular"

        var apiKey = "f68f24fa05d21e593c507df4e2eb1c12"
    }

}