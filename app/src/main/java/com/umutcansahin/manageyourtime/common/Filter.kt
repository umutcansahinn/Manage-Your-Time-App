package com.umutcansahin.manageyourtime.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    var sortedBy: SortedBy = SortedBy.DESC,
    var favoriteType: FavoriteType = FavoriteType.ALL_ITEM,
    var startTime: String = String.START_TIME,
    var endTime: String = String.END_TIME
):Parcelable

enum class SortedBy {
    DESC, ASC
}

enum class FavoriteType {
    ALL_ITEM,
    IS_FAVORITE_ITEM,
    IS_NOT_FAVORITE_ITEM
}