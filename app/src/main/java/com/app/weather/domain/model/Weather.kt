package com.app.weather.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class Weather(
    var the_temp : Int,
    var day : Int,
    var month : Int,
    var year: Int,
    var predictability: Int,
    var humidity: Int,
    var weather_state_abbr: String,
    var weather_state_name: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: String = ""

    init {
        id = "$day-$month-$year"
    }
}

fun getWeatherID(day: Int, month: Int, year: Int): String {
    return "$day-$month-$year"
}