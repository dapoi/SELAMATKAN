package com.dafdev.selamatkan.data.source.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "province")
data class ProvinceEntity(
    @ColumnInfo(name = "name")
    val name: String? = null,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String? = null
)
