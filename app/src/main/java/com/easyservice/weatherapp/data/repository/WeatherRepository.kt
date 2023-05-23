package com.easyservice.weatherapp.data.repository

import com.easyservice.weatherapp.domain.weather.WeatherInfo
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Flow<Result<WeatherInfo>>
}