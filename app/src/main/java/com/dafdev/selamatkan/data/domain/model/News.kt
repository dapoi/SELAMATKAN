package com.dafdev.selamatkan.data.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    var id: Int?,

    var author: String?,

    var title: String?,

    var publishedAt: String?,

    var urlToImage: String?,

    var description: String?,

    var url: String?,

    var content: String?,

    var isFav: Boolean
) : Parcelable