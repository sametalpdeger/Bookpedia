package com.plcoding.bookpedia.book.presentation.book_detail.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.plcoding.bookpedia.core.presentation.OutfitFontFamily

@Composable
fun BlurredImageBg(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }

    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult = if (size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image size"))
            }
        },
        onError = {
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )


    Box(
        modifier = modifier
            .height(400.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff9b6fff))
            ) {
                imageLoadResult?.getOrNull()?.let { painter ->
                    Image(
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .blur(20.dp)
                    )
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawRect(color = Color.Black.copy(alpha = 0.5f))
                    }
                }
            }

            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .background(Color.White)
            )
        }

        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .statusBarsPadding()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(200.dp))
            ElevatedCardContent(
                imageLoadResult = imageLoadResult,
                onFavoriteClick = onFavoriteClick,
                painter = painter,
                isFavorite = isFavorite
            )
        }

    }

    Spacer(modifier = Modifier.height(50.dp))
}

@Composable
private fun ElevatedCardContent(
    modifier: Modifier = Modifier,
    imageLoadResult: Result<Painter>? = null,
    onFavoriteClick: () -> Unit,
    painter: Painter,
    isFavorite: Boolean
) {
    ElevatedCard(
        modifier = Modifier
            .width(200.dp)
            .aspectRatio(2 / 3f),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Transparent,

            ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 15.dp
        )
    ) {
        when (imageLoadResult) {
            null -> CircularProgressIndicator()
            else -> {
                Box {
                    if (imageLoadResult.isSuccess) Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent),
                        contentScale = ContentScale.Crop,
                    ) else Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xffddc7ff)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No image",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = OutfitFontFamily(),
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.Transparent)
                                .align(Alignment.Center)
                        )
                    }

                    IconButton(
                        onClick = onFavoriteClick,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(
                                        Color(0xff9b6fff),
                                        Color.Transparent,
                                    ),
                                    radius = 70f
                                )
                            )
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = Color.White,
                        )
                    }
                }
            }
        }
    }
}