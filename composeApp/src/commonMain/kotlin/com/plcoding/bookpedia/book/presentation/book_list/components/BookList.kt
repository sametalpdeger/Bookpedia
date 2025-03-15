package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
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
import com.plcoding.bookpedia.core.components.EndlessLazyColumn


@Composable
fun BookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    onLoadMore: () -> Unit,
    isLoading: Boolean,
    scrollState: LazyListState = rememberLazyListState(),
    modifier: Modifier = Modifier
) {
    EndlessLazyColumn(
        state = scrollState,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .widthIn(max = 800.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        loadMore = onLoadMore,
        loading = isLoading,
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