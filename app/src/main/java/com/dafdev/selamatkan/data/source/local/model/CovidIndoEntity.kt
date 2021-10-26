package com.dafdev.selamatkan.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "covid")
data class CovidIndoEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "positif")
    val positif: Int?,

    @ColumnInfo(name = "sembuh")
    val sembuh: Int?,

    @ColumnInfo(name = "meninggal")
    val meninggal: Int?
)
