package com.dafdev.selamatkan.data.source.remote.model

import com.squareup.moshi.Json

data class HospitalCovidResponse(

    @Json(name = "hospitals")
    val hospitals: List<HospitalsCovidItem?>? = null,

    @Json(name = "status")
    val status: Int? = null
)

data class HospitalsCovidItem(

    @Json(name = "address")
    val address: String? = null,

    @Json(name = "bed_availability")
    val bed_availability: Int?,

    @Json(name = "phone")
    val phone: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "id")
    val id: String? = null,

    @Json(name = "queue")
    val queue: Int? = null,

    @Json(name = "info")
    val info: String? = null
)
