package com.dafdev.selamatkan.utils

import com.dafdev.selamatkan.data.domain.model.*
import com.dafdev.selamatkan.data.source.local.model.ProvinceEntity
import com.dafdev.selamatkan.data.source.remote.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapProvinceResponseToEntity(provinceResponse: List<ProvincesItem>): List<ProvinceEntity> {
        val listProv = ArrayList<ProvinceEntity>()
        provinceResponse.map {
            val provinceEntity = ProvinceEntity(
                it.name,
                it.id,
            )
            listProv.add(provinceEntity)
        }
        return listProv
    }

    fun mapDataCovidResponseToCovidProvince(dataResponse: List<RegionsItem?>?): Flow<List<CovidProv>> {
        val listCovid = ArrayList<CovidProv>()
        dataResponse?.map {
            CovidProv(
                it?.name,
                it?.numbers?.infected,
                it?.numbers?.recovered,
                it?.numbers?.fatal
            ).let { data ->
                listCovid.add(data)
            }
        }
        return flowOf(listCovid)
    }

    fun mapArticlesToNews(articles: List<ArticlesItem>): Flow<List<News>> {
        ArrayList<News>().let { listNews ->
            articles.map {
                val news = News(
                    it.title.toString(),
                    it.publishedAt.toString(),
                    it.urlToImage.toString(),
                    it.url.toString(),
                    it.content.toString()
                )
                listNews.add(news)
            }
            return flowOf(listNews)
        }
    }

    fun mapSearchArticlesToSearchNews(articles: List<ArticlesItemSearch>): Flow<List<SearchNews>> {
        ArrayList<SearchNews>().let { listNews ->
            articles.map {
                val news = SearchNews(
                    it.title.toString(),
                    it.publishedAt.toString(),
                    it.urlToImage.toString(),
                    it.url.toString(),
                    it.content.toString()
                )
                listNews.add(news)
            }
            return flowOf(listNews)
        }
    }

    fun mapCitiesResponseToCities(provincesItem: List<CitiesItem?>?): Flow<List<Cities>> {
        val listCities = ArrayList<Cities>()
        provincesItem?.map {
            val prov = Cities(
                it?.id,
                it?.name
            )
            listCities.add(prov)
        }
        return flowOf(listCities)
    }

    fun mapHospitalCovidResponseToHospitalCovid(hospitalsCovidItem: List<HospitalsCovidItem?>?): Flow<List<HospitalCovid>> {
        val listHospital = ArrayList<HospitalCovid>()
        hospitalsCovidItem?.map {
            val hospitalCovid = HospitalCovid(
                it?.id,
                it?.name,
                it?.address,
                it?.phone,
                it?.info
            )
            listHospital.add(hospitalCovid)
        }
        return flowOf(listHospital)
    }

    fun mapHospitalNonCovidResponseToHospitalNonCovid(hospitalsNonCovidItem: List<HospitalsNonCovidItem?>?): Flow<List<HospitalNonCovid>> {
        val listHospital = ArrayList<HospitalNonCovid>()
        hospitalsNonCovidItem?.map {
            val hospitalNonCovid = HospitalNonCovid(
                it?.id,
                it?.name,
                it?.address,
                it?.phone,
                it?.available_beds?.get(0)?.info
            )
            listHospital.add(hospitalNonCovid)
        }
        return flowOf(listHospital)
    }

    fun mapHospitalDetailResponseToHospitalDetail(hospitalDetail: List<BedDetailItem?>?): Flow<List<DetailHospital>> {
        val listDetail = ArrayList<DetailHospital>()
        hospitalDetail?.map {
            val detail = DetailHospital(
                it?.stats?.title,
                it?.stats?.bed_available,
                it?.stats?.bed_empty,
                it?.time
            )
            listDetail.add(detail)
        }
        return flowOf(listDetail)
    }

    fun mapLocationResponseToLocation(locationResponse: DataMapHospital?): Flow<Location> {
        return flowOf(
            Location(
                locationResponse?.gmaps,
                locationResponse?.address,
                locationResponse?.name,
                locationResponse?.id,
                locationResponse?.lat,
                locationResponse?.long
            )
        )
    }
}