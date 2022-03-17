package com.dafdev.selamatkan.data.source.remote.model

import com.squareup.moshi.Json

data class CitiesResponse(

    @Json(name = "cities")
    val cities: List<CitiesItem?>? = null
)

data class CitiesItem(

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "id")
    val id: String? = null
)
