package com.dafdev.selamatkan.data.source.remote.response

import com.squareup.moshi.Json

data class ProvinceResponse(

	@Json(name="provinces")
	val provinces: List<ProvincesItem?>? = null
)

data class ProvincesItem(

	@Json(name="name")
	val name: String? = null,

	@Json(name="id")
	val id: String? = null
)
