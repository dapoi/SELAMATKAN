package com.dafdev.selamatkan.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ProvinceCovidResponse(

    @field:SerializedName("provinsi")
    val provinsi: String? = null,

    @field:SerializedName("meninggal")
    val meninggal: Int? = null,

    @field:SerializedName("sembuh")
    val sembuh: Int? = null,

    @field:SerializedName("dirawat")
    val dirawat: Int? = null,

    @field:SerializedName("kasus")
    val kasus: Int? = null
)
