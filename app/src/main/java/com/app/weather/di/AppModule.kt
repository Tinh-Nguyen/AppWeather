package com.app.weather.di

import android.app.Application
import androidx.room.Room
import com.app.weather.data.data_source.local.WeatherLocalDatabase
import com.app.weather.data.respository.WeatherRepositoryImpl
import com.app.weather.domain.respository.WeatherRepository
import com.app.weather.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun  providerWeatherDatabase(application: Application): WeatherLocalDatabase = Room.databaseBuilder(application,
        WeatherLocalDatabase::class.java,
        WeatherLocalDatabase.DB_Name).allowMainThreadQueries().build()

    @Provides
    @Singleton
    fun  providerWeatherRespository(weatherLocalDatabase: WeatherLocalDatabase) : WeatherRepository = WeatherRepositoryImpl(
        weatherDao = weatherLocalDatabase.weatherLocalDao
    )

    @Provides
    @Singleton
    fun  providerWeatherUseCase(weatherRepository: WeatherRepository) = WeatherUseCase(
        getWeather = GetWeather(weatherRepository),
        addWeather = AddWeather(weatherRepository),
        updateWeather = UpdateWeather(weatherRepository),
        removeWeather = RemoveWeather(weatherRepository)
    )
}