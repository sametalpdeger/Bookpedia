package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.UiText

data class BookListState(
    val searchParams: SearchParams = SearchParams(
        query = "The C Programming Language",
        limit = 10,
        offset = 0,
    ),
    val searchResults: MutableList<Book> = mutableListOf(),
    val favoriteBooks: MutableList<Book> = mutableListOf(),
    val isLoading: Boolean = true,
    val isWholeListLoading: Boolean = true,
    val errorMessage: UiText? = null,
    val selectedTabIndex: Int = 0,
)

data class SearchParams(
    val query: String,
    val limit: Int,
    val offset: Int,
)