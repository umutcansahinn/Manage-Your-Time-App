package com.umutcansahin.manageyourtime.common.filter

import android.os.Parcelable
import com.umutcansahin.manageyourtime.common.extensions.END_TIME
import com.umutcansahin.manageyourtime.common.extensions.START_TIME
import kotlinx.parcelize.Parcelize

@Parcelize
data class Filter(
    var sortedBy: SortedBy = SortedBy.DESC,
    var favoriteType: FavoriteType = FavoriteType.ALL_ITEM,
    var startTime: String = String.START_TIME,
    var endTime: String = String.END_TIME
):Parcelable


