package com.app.weather.presentation.home_screen.weather_detail

import com.app.weather.domain.model.Weather
import java.util.*

sealed class WeatherDetailState(calendar: Calendar, weather: Weather? = null, error: String? = null) {
    class Success(calendar: Calendar, data: Weather) : WeatherDetailState(calendar, data)
    class Error(calendar: Calendar, error: String): WeatherDetailState(calendar, error = error)
    class Loading(calendar: Calendar) : WeatherDetailState(calendar)
}