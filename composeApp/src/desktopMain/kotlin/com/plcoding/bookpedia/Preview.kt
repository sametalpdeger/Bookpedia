package com.plcoding.bookpedia

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailScreen
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailState

@Preview
@Composable
fun BookSearchBarPreview() {
}

@Preview
@Composable
fun BookDetailScreenPreview() {
    BookDetailScreen(
        state = BookDetailState(
            book = Book(
                id = "1",
                title = "Kotlin",
                imageUrl = "https://images.gr-assets.com/books/1474287222l/10.jpg",
                authors = listOf("Andrey Breslav"),
                description = "Kotlin is a modern programming language that makes developers happier. It is designed to be more expressive, concise, safer, and fun, all at the same time.",
                languages = listOf("English", "Russian"),
                firstPublishYear = "2022",
                averageRating = 4.5,
                ratingCount = 100,
                numEditions = 100,
                numPages = 100,
                uniqueId = "1",
            ),
            isFavorite = true,
            isLoading = false,
        ),
        onAction = {}
    )
}