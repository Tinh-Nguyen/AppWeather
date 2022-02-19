package com.app.weather.domain.use_case

import com.app.weather.domain.model.Weather
import com.app.weather.domain.respository.WeatherRepository

class RemoveWeather (
    private val weatherRepository: WeatherRepository
)  {
    suspend operator fun invoke(weather: Weather){
        weatherRepository.removeWeather(weather)
    }
}