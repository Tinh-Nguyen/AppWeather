package com.app.weather.presentation.home_screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.app.weather.presentation.home_screen.header.Header
import com.app.weather.presentation.home_screen.weather_detail.WeatherDetail

@Composable
fun HomeScreen() {
    Box(modifier = Modifier.fillMaxSize()){
        Column() {
            Header()
            WeatherDetail(modifier = Modifier.weight(1f))
        }

    }

}