package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.data.Book

sealed interface BookListAction {
    data class onSearchQueryChanged(val query: String): BookListAction
    data class onBookClick(val book: Book): BookListAction
    data class onTabChanged(val index: Int): BookListAction
}