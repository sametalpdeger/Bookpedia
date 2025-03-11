package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.bookpedia.book.presentation.book_list.BookListAction
import com.plcoding.bookpedia.book.presentation.book_list.BookListState
import com.plcoding.bookpedia.core.presentation.OutfitFontFamily

@Composable
fun ColumnScope.HorizontalPagerContent(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    state: BookListState,
    onAction: (BookListAction) -> Unit,
    searchResultsScrollState: LazyListState,
    favoritesScrollState: LazyListState

) {
    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth(),
        userScrollEnabled = true,
    ) { pageIndex ->
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center

        ) {
            when (pageIndex) {
                0 -> {
                    if (state.isLoading) {
                        CircularProgressIndicator()
                    } else {
                        when {
                            state.errorMessage != null -> {
                                Text(
                                    text = state.errorMessage.asString(),
                                    fontFamily = OutfitFontFamily(),
                                    fontSize = 18.sp,
                                    color = Color(0xffdd0452),
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(16.dp)
                                )
                            }

                            state.searchResults.isEmpty() -> {
                                Text(
                                    text = "No results found",
                                    fontFamily = OutfitFontFamily(),
                                    fontSize = 18.sp,
                                    color = Color(0xd2000000),
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .padding(16.dp)
                                )
                            }

                            else -> {
                                BookList(
                                    books = state.searchResults,
                                    onBookClick = {
                                        onAction(BookListAction.onBookClick(it))
                                    },
                                    scrollState = searchResultsScrollState,
                                )
                            }
                        }
                    }
                }

                1 -> {
                    if (state.favoriteBooks.isEmpty()) {
                        Text(
                            text = "No results found",
                            fontFamily = OutfitFontFamily(),
                            fontSize = 18.sp,
                            color = Color(0xd2000000),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    } else {
                        BookList(
                            books = state.favoriteBooks,
                            onBookClick = {
                                onAction(BookListAction.onBookClick(it))
                            },
                            scrollState = favoritesScrollState,
                        )
                    }
                }
            }

        }
    }
}