package com.dafdev.selamatkan.data.source.remote.model

import com.squareup.moshi.Json

data class HospitalNonCovidResponse(

    @Json(name="hospitals")
	val hospitals: List<HospitalsNonCovidItem?>? = null,

    @Json(name="status")
	val status: Int? = null
)

data class AvailableBedsItem(

	@Json(name="bed_class")
	val bed_class: String? = null,

	@Json(name="room_name")
	val room_name: String? = null,

	@Json(name="available")
	val available: Int? = null,

	@Json(name="info")
	val info: String? = null
)

data class HospitalsNonCovidItem(

    @Json(name="address")
	val address: String? = null,

    @Json(name="available_beds")
	val available_beds: List<AvailableBedsItem?>? = null,

    @Json(name="phone")
	val phone: String? = null,

    @Json(name="name")
	val name: String? = null,

    @Json(name="id")
	val id: String? = null
)
