package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.search_hint
import com.plcoding.bookpedia.core.presentation.OutfitFontFamily
import org.jetbrains.compose.resources.stringResource

@Composable
fun BookSearchBar(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onImeSearchAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            handleColor = Color(0xff9a60f9),
            backgroundColor = Color(0xff9a60f9),
        )
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { onSearchQueryChanged(it) },
            label = { /*TODO*/ },
            singleLine = true,
            shape = RoundedCornerShape(100),
            textStyle = TextStyle(
                color = Color(0xd2000000), fontSize = 20.sp,
                fontFamily = OutfitFontFamily(),
                fontWeight = FontWeight.Medium
            ),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color(0xff7436e7),
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedBorderColor = Color(0xff9a60f9),
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color(0x76000000),
                    modifier = Modifier.width(40.dp).height(40.dp).padding(start = 8.dp)
                )
            },
            placeholder = {
                Text(
                    text = stringResource(Res.string.search_hint),
                    color = Color(0x78000000),
                    fontSize = 20.sp,
                    fontFamily = OutfitFontFamily(),
                    fontWeight = FontWeight.Medium,
                )
            },
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .minimumInteractiveComponentSize(),
            keyboardActions = KeyboardActions(onSearch = {
                onImeSearchAction()
            }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Search
            ),
            trailingIcon = {
                AnimatedVisibility(
                    visible = searchQuery.isNotEmpty(),
                ) {
                    IconButton(
                        onClick = { onSearchQueryChanged("") },

                        ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color(0x8c000000),
                            modifier = Modifier
                                .width(40.dp)
                                .height(40.dp)
                                .padding(end = 8.dp)
                        )
                    }
                }
            }
        )

    }
}

