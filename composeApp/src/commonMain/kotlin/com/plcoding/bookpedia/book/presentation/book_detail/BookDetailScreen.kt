@file:OptIn(ExperimentalLayoutApi::class)

package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.presentation.book_detail.components.BlurredImageBg
import com.plcoding.bookpedia.book.presentation.book_detail.components.BookChip
import com.plcoding.bookpedia.book.presentation.book_detail.components.TitledContent
import com.plcoding.bookpedia.core.presentation.OutfitFontFamily
import kotlin.math.round

@Composable
fun BookDetailScreenRoot(
    viewModel: BookDetailViewModel,
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookDetailScreen(
        state = state,
        onAction = {
            when (it) {
                is BookDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }

            viewModel.onAction(it)
        }
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookDetailScreen(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BlurredImageBg(
            imageUrl = state.book?.imageUrl,
            isFavorite = state.isFavorite,
            onFavoriteClick = {
                onAction(BookDetailAction.OnFavoriteClick)
            },
            onBackClick = {
                onAction(BookDetailAction.OnBackClick)
            },

            modifier = Modifier.fillMaxSize()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .padding(16.dp)
                .widthIn(max = 800.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (state.book != null) {
                BookDetailHeader(state)
                BookDetailChipContent(state)
                BookDetailDescription(state)
            }
        }
    }
}


@Composable
private fun BookDetailHeader(
    state: BookDetailState,
) {
    Text(
        text = state.book!!.title,
        style = TextStyle(
            fontSize = 25.sp,
            fontFamily = OutfitFontFamily(),
            fontWeight = FontWeight.SemiBold,
        ),
        maxLines = 2,
    )

    Text(
        text = state.book.authors.joinToString(", "),
        style = TextStyle(
            fontSize = 17.sp,
            fontFamily = OutfitFontFamily(),
            fontWeight = FontWeight.Medium,
        ),
        maxLines = 2,
    )

}

@Composable
private fun BookDetailChipContent(
    state: BookDetailState,
) {
    FlowRow(
        modifier = Modifier
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalArrangement = Arrangement.Center,
    ) {
        state.book!!.averageRating?.let { rating ->

            TitledContent(
                title = "Rating",
            ) {
                BookChip {
                    Text(
                        text = "${round(rating * 10) / 10.0}",
                        fontSize = 19.sp,
                        color = Color.White,
                        fontFamily = OutfitFontFamily(),
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                    )

                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            }
        }

        state.book.numPages?.let { numPages ->
            TitledContent(
                title = "Pages",
            ) {
                BookChip {
                    Text(
                        text = "$numPages",
                        fontSize = 19.sp,
                        color = Color.White,
                        fontFamily = OutfitFontFamily(),
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                    )
                }
            }
        }

        if (state.book.firstPublishYear != null) {
            TitledContent(
                title = "Published",
            ) {
                BookChip {
                    Text(
                        text = "${state.book.firstPublishYear}",
                        fontSize = 19.sp,
                        color = Color.White,
                        fontFamily = OutfitFontFamily(),
                        fontWeight = FontWeight.Medium,
                        maxLines = 2,
                    )
                }
            }
        }

        val languages = state.book.languages

        if (languages.isNotEmpty()) {
            TitledContent(
                title = "Languages",
            ) {
                FlowRow(
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                ) {
                    languages.forEach { language ->
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                        ) {
                            BookChip {
                                Text(
                                    text = language.uppercase(),
                                    fontSize = 17.sp,
                                    color = Color.White,
                                    fontFamily = OutfitFontFamily(),
                                    fontWeight = FontWeight.Medium,
                                    maxLines = 2,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookDetailDescription(
    state: BookDetailState,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {

        Text(
            text = "Description",
            style = TextStyle(
                fontSize = 23.sp,
                fontFamily = OutfitFontFamily(),
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
            ),
            modifier = Modifier
        )

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(top = 10.dp)
            )
        } else {
            val isStateDescriptionEmpty = state.book!!.description.isNullOrBlank()
            val description =
                if (isStateDescriptionEmpty) "No description provided." else state.book.description!!

            Text(
                text = description,
                style = TextStyle(
                    fontSize = 17.sp,
                    fontFamily = OutfitFontFamily(),
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Justify,
                    color = if (isStateDescriptionEmpty) Color(0xab000000) else Color(
                        0xdf000000
                    ),
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

