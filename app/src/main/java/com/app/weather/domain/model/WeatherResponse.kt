package com.app.weather.domain.model
import com.google.gson.annotations.SerializedName
class WeatherResponse {
    @SerializedName("id")
    var id: Number = 0
    @SerializedName("weather_state_name")
    var weather_state_name: String =""
    @SerializedName("weather_state_abbr")
    var weather_state_abbr: String = ""
    @SerializedName("wind_direction_compass")
    var wind_direction_compass: String = ""
    @SerializedName("min_temp")
    var min_temp: Float = 0f
    @SerializedName("max_temp")
    var max_temp: Float = 0f
    @SerializedName("the_temp")
    var the_temp: Float = 0f
    @SerializedName("wind_speed")
    var wind_speed: Float = 0f
    @SerializedName("wind_direction")
    var wind_direction: Float = 0f
    @SerializedName("air_pressure")
    var air_pressure: Float = 0f
    @SerializedName("humidity")
    var humidity: Int = 0
    @SerializedName("visibility")
    var visibility: Float = 0f
    @SerializedName("predictability")
    var predictability: Int = 0
}
enum class CallApiState {
    Loading,
    Success,
    Error
}
data class WeatherResponseState(val state: CallApiState, val data: List<WeatherResponse>? = null, val error : String? = null )