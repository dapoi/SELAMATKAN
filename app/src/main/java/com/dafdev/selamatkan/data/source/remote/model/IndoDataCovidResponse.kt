package com.dafdev.selamatkan.data.source.remote.model

import com.squareup.moshi.Json

data class IndoDataCovidResponse(

    @Json(name = "meninggal")
    val meninggal: Int? = null,

    @Json(name = "positif")
    val positif: Int? = null,

    @Json(name = "sembuh")
    val sembuh: Int? = null,

    @Json(name = "dirawat")
    val dirawat: Int? = null,

    @Json(name = "lastUpdate")
    val lastUpdate: String? = null
)
