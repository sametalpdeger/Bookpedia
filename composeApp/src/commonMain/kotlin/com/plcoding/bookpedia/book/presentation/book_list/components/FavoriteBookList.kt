package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.plcoding.bookpedia.book.domain.Book


@Composable
fun FavoriteBookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    scrollState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = scrollState,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .widthIn(max = 800.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(
            books,
            key = { it.uniqueId },
        ) { book ->
            BookListItem(
                book = book,
                onClick = { onBookClick(book) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}