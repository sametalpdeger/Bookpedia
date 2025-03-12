package com.plcoding.bookpedia

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.plcoding.bookpedia.book.data.repository.DefaultBookRepository
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.core.data.HttpClientFactory
import com.plcoding.bookpedia.core.presentation.AppTheme
import com.plcoding.bookpedia.core.presentation.OutfitFontFamily
import com.plcoding.bookpedia.core.presentation.ScreenSize
import com.plcoding.bookpedia.core.presentation.observeScreenSize
import io.ktor.client.engine.HttpClientEngine
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App(engine: HttpClientEngine) {
    AppTheme {
        Layout(
            content = {
                val coroutineScope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    observeScreenSize(coroutineScope)
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val screenSize = ScreenSize.collectAsStateWithLifecycle()

                    BasicText(
                        text = "${screenSize.value.first} x ${screenSize.value.second}",
                        style = TextStyle(
                            fontSize = 10.sp,
                            fontFamily = OutfitFontFamily(),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .zIndex(3f)
                            .offset(x = 2.dp, y = 2.dp) // Offset from the top-left corner
                    )

                    BookListScreenRoot(
                        viewModel = remember {
                            BookListViewModel(
                                datasource = DefaultBookRepository(
                                    remoteBookDataSource = KtorRemoteBookDataSource(
                                        httpClient = HttpClientFactory.create(
                                            engine = engine
                                        )
                                    )
                                )
                            )
                        },
                        onBookClick = { /*TODO*/ },
                    )
                }
            },
            measurePolicy = { measurables, constraints ->
                // Use the max width and height from the constraints
                val width = constraints.maxWidth
                val height = constraints.maxHeight

                ScreenSize.value = Pair(width, height)

                // Measure and place children composables
                val placeables = measurables.map { measurable ->
                    measurable.measure(constraints)
                }

                layout(width, height) {
                    var yPosition = 0
                    placeables.forEach { placeable ->
                        placeable.placeRelative(x = 0, y = yPosition)
                        yPosition += placeable.height
                    }
                }
            }
        )
    }
}