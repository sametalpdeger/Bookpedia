package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Button
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
        ) {
            when (pageIndex) {
                0 -> {
                    when {
                        state.errorMessage != null -> {
                            ErrorResult(
                                onAction = onAction,
                                errorMessage = state.errorMessage.asString()
                            )
                        }

                        state.searchResults.isEmpty() -> {
                            ErrorResult(
                                onAction = onAction,
                                errorMessage = "No results found"
                            )
                        }

                        else -> {
                            BookList(
                                books = state.searchResults,
                                onBookClick = {
                                    onAction(BookListAction.onBookClick(it))
                                },
                                scrollState = searchResultsScrollState,
                                onLoadMore = {
                                    onAction(BookListAction.onLoadMore)
                                },
                                isLoading = state.isLoading,
                            )
                        }
                    }

                }

                1 -> {
                    if (state.favoriteBooks.isEmpty()) {
                        Text(
                            text = "You haven't added any favorite book yet",
                            fontFamily = OutfitFontFamily(),
                            fontSize = 18.sp,
                            color = Color(0xd2000000),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    } else {
                        FavoriteBookList(
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

@Composable
private fun ErrorResult(
    onAction: (BookListAction) -> Unit,
    errorMessage: String,
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = errorMessage,
            fontFamily = OutfitFontFamily(),
            fontSize = 18.sp,
            color = Color(0xd2000000),
            modifier = Modifier
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                onAction(BookListAction.onRefresh)
            },
            content = {
                Text(
                    text = "Try again",
                    fontFamily = OutfitFontFamily(),
                    fontSize = 18.sp,
                    color = Color(0xffffffff),
                )
            }
        )
    }
}