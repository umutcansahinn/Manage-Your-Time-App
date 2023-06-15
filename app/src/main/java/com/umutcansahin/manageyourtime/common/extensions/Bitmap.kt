package com.umutcansahin.manageyourtime.common.extensions

import android.graphics.Bitmap

fun makeSmallerImage(image: Bitmap, maximumSize: Int): Bitmap {
    var width = image.width
    var height = image.height

    val bitmapRatio: Float = width.toFloat() / height.toFloat()

    if (bitmapRatio > 1) {
        width = maximumSize
        height = (width / bitmapRatio).toInt()
    } else {
        height = maximumSize
        width = (height * bitmapRatio).toInt()
    }

    return Bitmap.createScaledBitmap(image, width, height, true)
}