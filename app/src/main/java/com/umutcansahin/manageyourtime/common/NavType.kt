package com.umutcansahin.manageyourtime.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class NavType: Parcelable {
    ADD_NEW_ITEM,
    UPDATE_ITEM
}