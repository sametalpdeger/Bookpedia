package com.plcoding.bookpedia.book.data.database

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json

object RoomConverters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun toString(value: List<String>): String {
        return Json.encodeToString(value)
    }
}