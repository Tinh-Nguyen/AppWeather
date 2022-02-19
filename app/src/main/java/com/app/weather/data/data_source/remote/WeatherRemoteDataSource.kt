package com.app.weather.data.data_source.remote

import com.app.weather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

class WeatherRemoteDataSource : IWeatherDataSource {
    override fun getWeathers(): Flow<List<Weather>> {
        TODO("Not yet implemented")
    }
}