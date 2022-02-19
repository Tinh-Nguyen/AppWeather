package com.app.weather.data.data_source.local

import androidx.room.*
import com.app.weather.domain.model.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherLocalDao {
    @Query("select * from weather where id = :id")
    fun getWeather(id: String) : Weather?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeather(weather: Weather)

    @Update
    suspend fun updateWeather(weather: Weather)

    @Delete
    suspend fun removeWeather(weather: Weather)
}