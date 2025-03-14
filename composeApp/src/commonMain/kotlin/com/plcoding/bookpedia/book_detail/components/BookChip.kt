package com.plcoding.bookpedia.book_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

enum class BookChipSize {
    SMALL, REGULAR
}

@Composable
fun BookChip(
    modifier: Modifier = Modifier,
    size: BookChipSize = BookChipSize.REGULAR,
    chipContent: @Composable RowScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .widthIn(
                min = when (size) {
                    BookChipSize.SMALL -> 50.dp
                    BookChipSize.REGULAR -> 100.dp
                }
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xffd4a0ff))
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            ),
        contentAlignment = Alignment.Center,

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = chipContent
        )
    }
}