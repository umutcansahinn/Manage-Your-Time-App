package com.umutcansahin.manageyourtime.common.extensions

fun Long.convertToMinuteAndSecond(): String {
    val minute = (this / 60_000).toInt()
    val second = ((this / 1_000) % 60).toInt()
    return String.format("%02d:%02d", minute, second)
}

fun String.convertToMillisecond(): Long {
    return this.toLong() * 60_000
}

fun Long.convertFromMillisecondToMinute(): String {
    return (this / 60_000).toString()
}

fun String.toIntAndCheckIfEqualsZero(): Boolean {
    return this.toInt() == 0
}