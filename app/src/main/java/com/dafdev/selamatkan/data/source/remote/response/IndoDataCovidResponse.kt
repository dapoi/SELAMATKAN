package com.dafdev.selamatkan.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class IndoDataCovidResponse(

	@SerializedName("meninggal")
	val meninggal: Int? = null,

	@SerializedName("positif")
	val positif: Int? = null,

	@SerializedName("sembuh")
	val sembuh: Int? = null,

	@SerializedName("dirawat")
	val dirawat: Int? = null,

	@SerializedName("lastUpdate")
	val lastUpdate: String? = null
)
