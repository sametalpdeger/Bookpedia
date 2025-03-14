package com.plcoding.bookpedia.book.data.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect class BookDatabaseConstructor: RoomDatabaseConstructor<FavoriteBookDatabase> {
    override fun initialize(): FavoriteBookDatabase
}