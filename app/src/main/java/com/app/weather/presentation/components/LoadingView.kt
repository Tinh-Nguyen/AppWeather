package com.app.weather.presentation.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingView(content: @Composable() (color: Color)->Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = Color.White,
        targetValue = Color(180,180,180),
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2000
            },
            repeatMode = RepeatMode.Reverse
        )
    )
    content(color = color)
}