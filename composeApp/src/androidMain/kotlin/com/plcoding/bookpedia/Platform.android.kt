package com.plcoding.bookpedia

import android.os.Build
import kotlinx.coroutines.flow.MutableStateFlow

public val screenSize =  MutableStateFlow(Pair(0, 0))

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()