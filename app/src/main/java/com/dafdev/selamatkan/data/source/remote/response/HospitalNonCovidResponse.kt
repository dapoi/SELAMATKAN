package com.dafdev.selamatkan.data.source.remote.response

import com.squareup.moshi.Json

data class HospitalNonCovidResponse(

    @Json(name = "hospitals")
    val hospitals: List<HospitalsNonCovidItem?>? = null,

    @Json(name = "status")
    val status: Int? = null
)

data class HospitalsNonCovidItem(

    @Json(name = "address")
    val address: String? = null,

    @Json(name = "bed_availability")
    val bedAvailability: Int? = null,

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
