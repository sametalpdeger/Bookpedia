package com.plcoding.bookpedia.book.data.dto

import kotlinx.serialization.Serializable

@Serializable(with = BookWorkDataSerializer::class)
data class BookWorkDto (
    val description: String?
)