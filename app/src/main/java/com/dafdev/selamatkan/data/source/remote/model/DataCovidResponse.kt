package com.dafdev.selamatkan.data.source.remote.model

import com.squareup.moshi.Json

data class DataCovidResponse(

	@Json(name="regions")
	val regions: List<RegionsItem?>? = null,

	@Json(name="name")
	val name: String? = null,

	@Json(name="numbers")
	val numbers: Numbers? = null,

	@Json(name="type")
	val type: String? = null,

	@Json(name="timestamp")
	val timestamp: Long? = null
)

data class Numbers(

	@Json(name="infected")
	val infected: Int? = null,

	@Json(name="recovered")
	val recovered: Int? = null,

	@Json(name="fatal")
	val fatal: Int? = null
)

data class RegionsItem(

	@Json(name="name")
	val name: String? = null,

	@Json(name="numbers")
	val numbers: Numbers? = null,

	@Json(name="type")
	val type: String? = null
)
