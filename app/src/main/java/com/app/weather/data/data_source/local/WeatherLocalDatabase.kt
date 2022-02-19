package com.app.weather.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.weather.domain.model.Weather

@Database(
    entities = [Weather::class],
    version = 2
)
abstract class WeatherLocalDatabase: RoomDatabase() {
    abstract  val  weatherLocalDao  : WeatherLocalDao
    fun  init(){

    }
    companion object{
        const val DB_Name = "weather_db"
    }
}