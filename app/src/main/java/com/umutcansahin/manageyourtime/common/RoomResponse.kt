package com.umutcansahin.manageyourtime.common


sealed interface RoomResponse {
    object Loading: RoomResponse
    object Success: RoomResponse
    data class Error(val errorType: ErrorType): RoomResponse
}

enum class ErrorType{
    ROOM_DEFAULT_ERROR,
    NAME_IS_NULL_ERROR,
    NAME_IS_BLANK_ERROR,
    TIME_IS_NULL_ERROR,
    TIME_IS_BLANK_ERROR,
    TIME_IS_EQUALS_ZERO_ERROR,
}