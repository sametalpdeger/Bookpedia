package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.data.Book

data class BookListState(
    val searchQuery: String = "Kotlin!",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
)
