package com.dafdev.selamatkan.data.source.response

import com.squareup.moshi.Json

data class HospitalLocationResponse(

    @Json(name = "dataHospitalDetail")
    val dataMap: DataMapHospital? = null,

    @Json(name = "status")
    val status: Int? = null
)

data class DataMapHospital(

    @Json(name = "gmaps")
    val gmaps: String? = null,

    @Json(name = "address")
    val address: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "id")
    val id: String? = null,

    @Json(name = "lat")
    val lat: String? = null,

    @Json(name = "long")
    val jsonMemberLong: String? = null
)
