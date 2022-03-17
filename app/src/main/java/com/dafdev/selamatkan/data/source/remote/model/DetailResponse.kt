package com.dafdev.selamatkan.data.source.remote.model

import com.squareup.moshi.Json

data class DetailResponse(

    @Json(name = "data")
    val data: DataHospitalDetail? = null,

    @Json(name = "status")
    val status: Int? = null
)

data class Stats(

    @Json(name = "bed_empty")
    val bed_empty: Int? = null,

    @Json(name = "title")
    val title: String? = null,

    @Json(name = "bed_available")
    val bed_available: Int? = null,

    @Json(name = "queue")
    val queue: Int? = null
)

data class BedDetailItem(

    @Json(name = "stats")
    val stats: Stats? = null,

    @Json(name = "time")
    val time: String? = null
)

data class DataHospitalDetail(

    @Json(name = "address")
    val address: String? = null,

    @Json(name = "phone")
    val phone: String? = null,

    @Json(name = "bedDetail")
    val bedDetail: List<BedDetailItem?>? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "id")
    val id: String? = null
)
