package com.example.peliculastest.model.bean

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
class Pelicula(): Serializable {

    @JsonProperty("id")
    var id: Long = 0
        get() {
            return field
        }
        set(value) {
            field = value
        }
    @JsonProperty("video")
    var video: Boolean = false
        get() {
            return field
        }
        set(value) {
            field = value
        }
    @JsonProperty("original_title")
    var tituloOriginal: String = "default"
        get() {
            return field
        }
        set(value) {
            field = value
        }
    @JsonProperty("title")
    var titulo: String = "default"
        get() {
            return field
        }
        set(value) {
            field = value
        }
    @JsonProperty("overview")
    var overview: String = "default"
        get() {
            return field
        }
        set(value) {
            field = value
        }
    @JsonProperty("poster_path")
    var posterPath: String = "defualt"
        get() {
            return field
        }
        set(value) {
            field = value
        }

    @JsonIgnore
    var esPopular: Int = 1
        get() {
            return field
        }
        set(value) {
            field = value
        }

    @JsonIgnore
    var esPlayNow: Int = 1
        get() {
            return field
        }
        set(value) {
            field = value
        }


    override fun equals(obj: Any?): Boolean {
        return if (obj is Pelicula) {
            val temp: Pelicula = obj as Pelicula
            if (temp.id === id) {
                true
            } else {
                false
            }
        } else {
            false
        }
    }
}