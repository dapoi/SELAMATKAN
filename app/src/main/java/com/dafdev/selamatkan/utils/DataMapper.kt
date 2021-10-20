package com.dafdev.selamatkan.utils

import com.dafdev.selamatkan.data.source.local.model.CitiesEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.data.source.remote.response.CitiesItem
import com.dafdev.selamatkan.data.source.remote.response.ProvincesItem

object DataMapper {

    fun mapProvinceResponseToEntity(province: List<ProvincesItem>): List<ProvinceEntity> {
        val listProv = ArrayList<ProvinceEntity>()
        province.map {
            val dataProvince = ProvinceEntity(
                it.name!!,
                it.id!!
            )
            listProv.add(dataProvince)
        }
        return listProv
    }

    fun mapCitiesResponseToEntity(cities: List<CitiesItem>): List<CitiesEntity> {
        val listCity = ArrayList<CitiesEntity>()
        cities.map {
            val dataCity = CitiesEntity(
                it.name!!,
                it.id!!
            )
            listCity.add(dataCity)
        }
        return listCity
    }
}