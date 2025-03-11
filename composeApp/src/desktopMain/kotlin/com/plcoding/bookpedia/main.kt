package com.plcoding.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        resizable = true,
        title = "CMP-Bookpedia",
    ) {
        App()
    }
}
