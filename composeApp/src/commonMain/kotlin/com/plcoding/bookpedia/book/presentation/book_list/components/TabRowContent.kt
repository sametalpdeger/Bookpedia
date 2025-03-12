package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.bookpedia.book.presentation.book_list.BookListAction
import com.plcoding.bookpedia.book.presentation.book_list.BookListState
import com.plcoding.bookpedia.core.presentation.OutfitFontFamily

@Composable
fun ColumnScope.TabRowContent(
    modifier: Modifier = Modifier,
    state: BookListState,
    onAction: (BookListAction) -> Unit
) {

    TabRow(
        selectedTabIndex = state.selectedTabIndex,
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 800.dp),
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                color = Color(0xff7436e7),
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[state.selectedTabIndex])
            )
        }
    ) {
        TabRowContentTab(
            index = 0,
            text = "Book list",
            state = state,
            onAction = onAction,
            modifier = Modifier
                .weight(1f)
        )

        TabRowContentTab(
            index = 1,
            text = "Favorites",
            state = state,
            onAction = onAction,
            modifier = Modifier
                .weight(1f)
        )
    }
}

@Composable
fun TabRowContentTab(
    index: Int,
    state: BookListState,
    text: String,
    onAction: (BookListAction) -> Unit,
    modifier: Modifier = Modifier
) {

    Tab(
        selected = state.selectedTabIndex == index,
        onClick = { onAction(BookListAction.onTabChanged(index)) },
        modifier = modifier,
        selectedContentColor = Color(0xff7436e7),
        unselectedContentColor = Color(0xff8a5ede),
    ) {
        Text(
            text = text,
            fontFamily = OutfitFontFamily(),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = 12.dp)
        )
    }
}