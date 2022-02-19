package com.app.weather.presentation.home_screen.header

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.weather.presentation.home_screen.weather_detail.WeatherDetailViewModel
import com.app.weather.presentation.home_screen.weather_detail.WeatherEvent
import java.util.*


@Composable
fun Header( viewModel:  WeatherDetailViewModel = hiltViewModel()) {

    val days = viewModel.listDate.value
    val currentDate = viewModel.currentDate.value
    fun onClick(calendar: Calendar){
        viewModel.onEvent(WeatherEvent.SelectDate(calendar))
    }
    Row{
        days.forEach {
            HeaderWeatherItem(it,
                currentDate,
                Modifier.weight(1f),
                onClick = { onClick(it)}
            )
        }

    }
}