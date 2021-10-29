package com.dafdev.selamatkan.data.source.local.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "province")
data class ProvinceEntity(
    @ColumnInfo(name = "name")
    val name: String,

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    val id: String
)
