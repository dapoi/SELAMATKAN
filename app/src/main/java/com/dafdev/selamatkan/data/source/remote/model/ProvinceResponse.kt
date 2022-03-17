package com.dafdev.selamatkan.data.source.remote.model

import com.squareup.moshi.Json

data class ProvinceResponse(

	@Json(name="provinces")
	val provinces: List<ProvincesItem>
)

data class ProvincesItem(

	@Json(name="name")
	val name: String,

	@Json(name="id")
	val id: String
)
