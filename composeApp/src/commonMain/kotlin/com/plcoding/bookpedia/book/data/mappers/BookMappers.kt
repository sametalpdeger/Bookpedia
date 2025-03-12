package com.plcoding.bookpedia.book.data.mappers

import com.plcoding.bookpedia.book.data.dto.SearchedBookDTO
import com.plcoding.bookpedia.book.domain.Book

const val IMAGE_URL_START_URL = "https://covers.openlibrary.org/b"

fun SearchedBookDTO.toBook(): Book {
    return Book(
        id = id,
        title = title,
        imageUrl =
        if (coverKey != null) "$IMAGE_URL_START_URL/ /${coverKey}-L.jpg"
        else "$IMAGE_URL_START_URL/id/${coverAlternativeKey}-L.jpg",
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = averageRating,
        ratingCount = ratingCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0,
    )
}