package com.dafdev.selamatkan.data.domain.usecase

import com.dafdev.selamatkan.data.domain.model.*
import com.dafdev.selamatkan.data.repository.IHealthRepository
import com.dafdev.selamatkan.data.source.local.model.CovidIndoEntity
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.vo.Resource
import kotlinx.coroutines.flow.Flow

class HealthInteractor(private val iHealthRepository: IHealthRepository) : HealthUseCase {
    override fun getDataCovidIndonesia(): Flow<Resource<CovidIndoEntity>> {
        return iHealthRepository.getDataCovidIndonesia()
    }

    override fun getDataCovidProv(): Flow<Resource<List<CovidProv>>> {
        return iHealthRepository.getDataCovidProv()
    }

    override fun getListProvinceHome(): Flow<Resource<List<ProvinceEntity>>> {
        return iHealthRepository.getListProvinceHome()
    }

    override fun getListProvinceInside(): Flow<Resource<List<Province>>> {
        return iHealthRepository.getListProvinceInside()
    }

    override fun getListCities(provinceId: String): Flow<Resource<List<Cities>>> {
        return iHealthRepository.getListCities(provinceId)
    }

    override fun getListCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<Resource<List<HospitalCovid>>> {
        return iHealthRepository.getListCovidHospital(provinceId, cityId)
    }

    override fun getListNonCovidHospital(
        provinceId: String,
        cityId: String
    ): Flow<Resource<List<HospitalNonCovid>>> {
        return iHealthRepository.getListNonCovidHospital(provinceId, cityId)
    }

    override fun getDetailCovidHospital(hospitalId: String): Flow<Resource<List<DetailHospital>>> {
        return iHealthRepository.getDetailCovidHospital(hospitalId)
    }

    override fun getDetailNonCovidHospital(hospitalId: String): Flow<Resource<List<DetailHospital>>> {
        return iHealthRepository.getDetailNonCovidHospital(hospitalId)
    }

    override fun getLocationHospitalMap(hospitalId: String): Flow<Resource<Location>> {
        return iHealthRepository.getLocationHospitalMap(hospitalId)
    }

    override fun getNews(): Flow<Resource<List<News>>> {
        return iHealthRepository.getNews()
    }
}