package com.app.weather.data.data_source.remote

import com.app.weather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface IWeatherDataSource {
    fun getWeathers() : Flow<List<Weather>>
}