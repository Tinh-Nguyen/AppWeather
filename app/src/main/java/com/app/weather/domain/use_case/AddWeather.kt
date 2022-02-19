package com.app.weather.domain.use_case

import com.app.weather.domain.model.Weather
import com.app.weather.domain.respository.WeatherRepository

class AddWeather(
    private val  weatherRepository: WeatherRepository
)  {
    suspend operator fun invoke(weather: Weather){
        weatherRepository.addWeather(weather)
    }
}