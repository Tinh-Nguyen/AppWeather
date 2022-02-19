package com.app.weather.domain.use_case

import com.app.weather.domain.respository.WeatherRepository

class GetWeather(
    private val  weatherRepository: WeatherRepository
)  {
    suspend operator fun invoke(day: Int,  month: Int, year: Int, refresh: Boolean = false) = weatherRepository.getWeather(day, month, year, refresh)
}