package com.plcoding.bookpedia

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.plcoding.bookpedia.book.presentation.book_list.components.BookSearchBar

@Preview
@Composable
fun BookSearchBarPreview() {
    BookSearchBar(
        searchQuery = "Kotlin!",
        onSearchQueryChanged = {},
        onImeSearchAction = {}
    )
}