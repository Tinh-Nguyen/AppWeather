package com.app.weather.presentation.home_screen.weather_detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.weather.domain.model.Weather
import com.app.weather.domain.use_case.WeatherUseCase
import com.app.weather.presentation.home_screen.header.HeaderEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WeatherDetailViewModel @Inject constructor(
    private  val  weatherUseCase: WeatherUseCase
): ViewModel()  {

    private val _listDate  = mutableStateOf<List<Calendar>>(getListDate(Calendar.getInstance()))
    val listDate : State<List<Calendar>> = _listDate

    private val _currentDate  = mutableStateOf<Calendar>(Calendar.getInstance())
    val currentDate : State<Calendar> = _currentDate

    private  val _weatherDetailState = mutableStateOf<WeatherDetailState?>(null)
    val weatherDetailState : State<WeatherDetailState?> = _weatherDetailState

    private val _weatherState  = mutableStateOf<Weather?>(null)
    val weatherState : State<Weather?> = _weatherState

    private val _isRefreshing = mutableStateOf<Boolean>(false)
    val isRefreshing : State<Boolean> = _isRefreshing



    private var job : Job? = null
    private suspend fun  getWeather(calendar: Calendar, refresh: Boolean = false){
        if(!refresh){
            _weatherDetailState.value = WeatherDetailState.Loading(calendar)
        }
        job?.cancel()
        job = weatherUseCase.getWeather(calendar.get(Calendar.DATE),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR), refresh).onEach {
            _weatherState.value = it
            _weatherDetailState.value = WeatherDetailState.Success(calendar = calendar, data = it )
            _isRefreshing.value = false
        }.catch {
            _weatherDetailState.value = WeatherDetailState.Error(calendar = calendar, error = it.toString() )
            _isRefreshing.value = false
        }.launchIn(viewModelScope)
    }


    fun onEvent(weatherEvent: WeatherEvent){
        when(weatherEvent){
            is WeatherEvent.SelectDate->{
                val calendar = weatherEvent.calendar
                _currentDate.value = calendar
                viewModelScope.launch{
                    getWeather(calendar)
                }
            }
            is WeatherEvent.Refresh->{
                _isRefreshing.value = true
                viewModelScope.launch{
                    getWeather(currentDate.value, true)
                }

            }
        }
    }
    private fun  getListDate(date: Calendar): List<Calendar>{
        var lsDates :MutableList<Calendar> = mutableListOf<Calendar>()
        lsDates.add(date)
        for (i in 1..6){
            val c1 = date.clone() as Calendar
            c1.add(Calendar.DATE,i)
            lsDates.add(c1)
        }
        return  lsDates
    }
    init {
        viewModelScope.launch{
            getWeather(Calendar.getInstance())
        }
    }
}