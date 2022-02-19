package com.app.weather.domain.use_case

data class WeatherUseCase(
    val getWeather: GetWeather,
    val addWeather: AddWeather,
    val removeWeather: RemoveWeather,
    val updateWeather: UpdateWeather
)