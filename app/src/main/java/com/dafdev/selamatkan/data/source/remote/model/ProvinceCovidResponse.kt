package com.dafdev.selamatkan.data.source.remote.model

import com.squareup.moshi.Json

data class ProvinceCovidResponse(

    @Json(name = "provinsi")
    val provinsi: String? = null,

    @Json(name = "meninggal")
    val meninggal: Int? = null,

    @Json(name = "sembuh")
    val sembuh: Int? = null,

    @Json(name = "kasus")
    val kasus: Int? = null
)
