package com.plcoding.bookpedia.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_list.components.BookSearchBar
import com.plcoding.bookpedia.book.presentation.book_list.components.HorizontalPagerContent
import com.plcoding.bookpedia.book.presentation.book_list.components.TabRowContent
import org.koin.compose.viewmodel.koinViewModel

// BookListScreenRoot is responsible for get viewModel then pass its state to actual UI
@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    BookListScreen(
        state = state.value,
        onAction = { action ->
            when (action) {
                is BookListAction.onBookClick -> onBookClick(action.book)
                else -> Unit
            }

            viewModel.onAction(action)
        }
    )
}


val gradientBgColors = listOf(
    Color(0xffa24bff),
    Color(0xff8336ff),
    Color(0xffff4bc9)
)

@Composable
fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchResultsScrollState = rememberLazyListState()
    val favoritesScrollState = rememberLazyListState()
    val pagerState = rememberPagerState(
        initialPage = state.selectedTabIndex,
        pageCount = { 2 },
    )

    // Sync tab changes to pager
    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    // Sync pager changes to tab
    LaunchedEffect(pagerState.currentPage) {
        onAction(BookListAction.onTabChanged(pagerState.currentPage))
    }

    // Sync search results scroll position to top
    LaunchedEffect(state.searchResults) {
        searchResultsScrollState.scrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.linearGradient(gradientBgColors)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BookSearchBar(
            searchQuery = state.searchParams.query,
            onSearchQueryChanged = {
                onAction(BookListAction.onSearchQueryChanged(it))
            },
            onImeSearchAction = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 700.dp)
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 16.dp)
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color(0xffe5d7ff))
                .weight(1f)
                .widthIn(max = 800.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Tabs
                TabRowContent(
                    state = state,
                    onAction = onAction
                )

                // Horizontal pager allows to switch between tabs using swipe gesture
                HorizontalPagerContent(
                    state = state,
                    onAction = onAction,
                    pagerState = pagerState,
                    searchResultsScrollState = searchResultsScrollState,
                    favoritesScrollState = favoritesScrollState
                )
            }
        }
    }
}