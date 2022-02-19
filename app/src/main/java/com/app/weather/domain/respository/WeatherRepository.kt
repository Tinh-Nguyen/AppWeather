package com.app.weather.domain.respository

import com.app.weather.domain.model.Weather
import com.app.weather.presentation.home_screen.weather_detail.WeatherDetailState
import kotlinx.coroutines.flow.Flow
import java.util.*

interface  WeatherRepository  {
    suspend fun getWeather(day: Int, month: Int, year: Int,  refresh: Boolean = false) : Flow<Weather>
    suspend fun addWeather(weather: Weather)
    suspend fun removeWeather(weather: Weather)
    suspend fun  updateWeather(weather: Weather )
}