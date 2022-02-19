package com.app.weather.services

import android.util.Log
import com.app.weather.domain.model.CallApiState
import com.app.weather.domain.model.WeatherResponse
import com.app.weather.domain.model.WeatherResponseState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.*

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.converter.gson.GsonConverterFactory

const val API_Get_WEATHER = "/api/location/1252431"
const val BASE_URL = "https://www.metaweather.com"
interface WeatherServiceAPIInterface {
    @GET("$API_Get_WEATHER/{year}/{month}/{day}")
    fun getCurrentWeatherData(@Path("day") day: Int, @Path("month") month: Int, @Path("year") year: Int): Call<List<WeatherResponse>>
}

object  WeaterAPI{
    suspend fun getWeather( day: Int,  month: Int, year: Int): Flow<WeatherResponseState> = callbackFlow{
        val call: Call<List<WeatherResponse>> = APIClient.weatherService.getCurrentWeatherData(day,month,year)
        val self = this
        var callback = object :  Callback<List<WeatherResponse>>{

            override fun onResponse(call: Call<List<WeatherResponse>>?, response: Response<List<WeatherResponse>>?) {
                if(response==null || response.body()==null){
                    trySendBlocking(WeatherResponseState(state = CallApiState.Error, error = "Error unknow"))
                    Log.d("getWeather","Error unknow")
                    return
                }
                val data: List<WeatherResponse> = response.body()!!
                trySendBlocking(WeatherResponseState(CallApiState.Success, response.body()!!, ""))
            }
            override fun onFailure(call: Call<List<WeatherResponse>?>, t: Throwable?) {
                Log.d("getWeather",t.toString())
                trySendBlocking(WeatherResponseState(CallApiState.Error, error = t.toString()))
                call.cancel()
            }
        }
        call.enqueue(callback)
        awaitClose { call.cancel() }
    }
}

object APIClient {
    private var retrofit: Retrofit? = null
    val weatherService: WeatherServiceAPIInterface
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(WeatherServiceAPIInterface::class.java)
        }
}
