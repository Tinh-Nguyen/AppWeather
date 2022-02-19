package com.app.weather.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable

@Composable
fun  AppTheme(content: @Composable() () -> Unit){
    MaterialTheme(
        typography = Typography(),
        content = content
    )
}