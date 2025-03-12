package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin!",
    val searchResults: List<Book> = (1..100).map {
        Book(
            id = "id$it",
            title = "title$it",
            imageUrl = "imageUrl$it",
            authors = listOf("author$it"),
            description = "description$it",
            languages = listOf("language$it"),
            firstPublishYear = "firstPublishYear$it",
            averageRating = it.toDouble(),
            ratingCount = it,
            numPages = it,
            numEditions = it,
        )
    },
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val selectedTabIndex: Int = 0,
)
