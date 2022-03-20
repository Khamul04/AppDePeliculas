package com.example.peliculastest.model.bean

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

@JsonIgnoreProperties(ignoreUnknown = true)
class PeliculasWeb {

    @JsonProperty("page")
    var page:Int = 0
        get() {
            return field
        }
        set(value) {
            field = value
        }

    @JsonProperty("results")
    var results: ArrayList<Pelicula> = ArrayList()
        get() {
            return field
        }
        set(value) {
            field = value
        }

    @JsonProperty("total_results")
    var totalResults:Int = 0
        get() {
            return field
        }
        set(value) {
            field = value
        }

    @JsonProperty("total_pages")
    var totalPages:Int = 0
        get() {
            return field
        }
        set(value) {
            field = value
        }

}