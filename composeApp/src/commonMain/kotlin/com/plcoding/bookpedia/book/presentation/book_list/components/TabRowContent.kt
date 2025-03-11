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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.presentation.book_list.BookListAction
import com.plcoding.bookpedia.book.presentation.book_list.BookListState
import com.plcoding.bookpedia.core.presentation.OutfitFontFamily
import com.plcoding.bookpedia.core.presentation.Screen
import com.plcoding.bookpedia.core.presentation.ScreenSizeName

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
            state = state,
            onAction = onAction,
            modifier = Modifier
                .weight(1f)
        )

        TabRowContentTab(
            index = 1,
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
    onAction: (BookListAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val screen = ScreenSizeName.collectAsStateWithLifecycle()
    val textPadding by remember {
        derivedStateOf {
            when (screen.value) {
                Screen.SM -> 16.dp
                Screen.MD -> 20.dp
                else -> 24.dp
            }
        }
    }

    val textSize by remember {
        derivedStateOf {
            when (screen.value) {
                Screen.SM -> 18.sp
                Screen.MD -> 20.sp
                else -> 22.sp
            }
        }
    }


    Tab(
        selected = state.selectedTabIndex == index,
        onClick = { onAction(BookListAction.onTabChanged(index)) },
        modifier = modifier,
        selectedContentColor = Color(0xff7436e7),
        unselectedContentColor = Color(0xff8a5ede),
    ) {
        Text(
            text = "Favorites ${screen.value}",
            fontSize = textSize,
            fontFamily = OutfitFontFamily(),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(vertical = textPadding)
        )
    }
}