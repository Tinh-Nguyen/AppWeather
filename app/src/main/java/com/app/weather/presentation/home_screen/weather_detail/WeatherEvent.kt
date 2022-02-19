package com.app.weather.presentation.home_screen.weather_detail

import java.util.*

sealed class WeatherEvent  {
    data class SelectDate(val calendar: Calendar) : WeatherEvent()
    data class Refresh(val calendar: Calendar) : WeatherEvent()
}