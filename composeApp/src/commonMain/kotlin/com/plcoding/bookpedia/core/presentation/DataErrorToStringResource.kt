package com.plcoding.bookpedia.core.presentation

import com.plcoding.bookpedia.core.domain.DataError

fun DataError.toUiText(): UiText {
    val stringRes = when(this) {
        DataError.Local.DISK_FULL -> "Your device disk is full"
        DataError.Local.UNKNOWN -> "Unknown error occurred"
        DataError.Remote.REQUEST_TIMEOUT -> "The request timed out"
        DataError.Remote.TOO_MANY_REQUESTS -> "Too many requests were made"
        DataError.Remote.SERVER -> "Server error occurred"
        DataError.Remote.NO_INTERNET -> "No internet connection"
        DataError.Remote.SERIALIZATION -> "Server error occurred"
        DataError.Remote.UNKNOWN -> "Unknown error occurred"
    }

    return UiText.DynamicString(stringRes)
}