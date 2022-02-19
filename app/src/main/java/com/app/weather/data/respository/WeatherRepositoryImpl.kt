package com.app.weather.data.respository

import android.util.Log
import com.app.weather.data.data_source.local.WeatherLocalDao
import com.app.weather.domain.model.CallApiState
import com.app.weather.domain.model.Weather
import com.app.weather.domain.model.WeatherResponse
import com.app.weather.domain.model.getWeatherID
import com.app.weather.domain.respository.WeatherRepository
import com.app.weather.services.WeaterAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.*

class WeatherRepositoryImpl( private  val weatherDao: WeatherLocalDao) : WeatherRepository {

    override suspend fun getWeather(day: Int,  month: Int, year: Int, refresh: Boolean): Flow<Weather> = channelFlow{
        Log.d("getWeather","111")
       val localWeather = weatherDao.getWeather(getWeatherID(day, month, year))
        if(localWeather != null && !refresh){
            Log.d("getWeather",localWeather.weather_state_name)
            Log.d("getWeather","getWeather from db")
            send(localWeather)
        }else{
            Log.d("getWeather","333")
            WeaterAPI.getWeather(day , month, year).onEach {
                Log.d("getweather","res:"+it.state.toString())
                if(it.state == CallApiState.Success && it.data != null && !it.data.isEmpty()){
                    var weathers : List<WeatherResponse> = it.data!!
                    var averageTemp = 0f;
                    var averageHumidity = 0
                    var averagePredictability = 0

                    val mapWeatherStateABBR = mutableMapOf<String,Int>()

                    for (item: WeatherResponse in weathers) {
                        averageTemp += item.the_temp
                        averageHumidity += item.humidity
                        averagePredictability += item.predictability

                        var countABBR = mapWeatherStateABBR.get(item.weather_state_abbr)
                        if(countABBR == null) countABBR =0;
                        mapWeatherStateABBR.put(item.weather_state_abbr, countABBR +1)

                    }
                    //get most popular ABBR WeatherStateABBR
                    var biggestCountABBR = 0;
                    var biggestABBR = ""

                    for (key in mapWeatherStateABBR.keys){
                        if(mapWeatherStateABBR.get(key)!! > biggestCountABBR){
                            biggestCountABBR = mapWeatherStateABBR.get(key)!!
                            biggestABBR = key

                        }
                    }
                    var biggestABBRName = weathers.find { weather->weather.weather_state_abbr==biggestABBR }?.weather_state_name
                    if(biggestABBRName.isNullOrEmpty())biggestABBRName = ""
                    averageTemp /= weathers.size
                    averageHumidity /= weathers.size
                    averagePredictability /= weathers.size

                    val weather: Weather = Weather(averageTemp.toInt(), day, month, year, averagePredictability, averageHumidity, biggestABBR, biggestABBRName)
                    weatherDao.addWeather(weather)
                    send(weather)
                }else{
                    throw ( Throwable("Error"))
                }
            }.launchIn(CoroutineScope(currentCoroutineContext()))
            awaitClose()
        }

    }

    override suspend fun addWeather(weather: Weather) {
        weatherDao.addWeather(weather)
    }

    override suspend fun removeWeather(weather: Weather) {
        weatherDao.removeWeather(weather )
    }

    override suspend fun updateWeather(weather: Weather) {
        weatherDao.addWeather(weather)
    }
}