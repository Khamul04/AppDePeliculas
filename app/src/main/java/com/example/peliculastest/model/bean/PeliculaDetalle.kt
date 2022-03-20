package com.example.peliculastest.model.bean

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class PeliculaDetalle(): Serializable{

    @JsonProperty("budget")
    var budget: Int = 0

    @JsonProperty("genres")
    var genres: List<Genre> = ArrayList()

    @JsonProperty("id")
    var id: Long = 0

    @JsonProperty("original_language")
    var original_language: String = ""

    @JsonProperty("overview")
    var overview: String = ""

    @JsonProperty("release_date")
    var release_date: String = ""

    @JsonProperty("revenue")
    var revenue: Int = 0

    @JsonProperty("runtime")
    var runtime: Int = 0

    @JsonProperty("tagline")
    var tagline: String = ""

    @JsonProperty("title")
    var title: String = ""

    @JsonProperty("video")
    var video: Boolean = true

    @JsonProperty("vote_average")
    var vote_average: Double = 0.0
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Genre() :Serializable{
    @JsonProperty("id")
    var id: Long = 0

    @JsonProperty("name")
    var name: String = ""
}














