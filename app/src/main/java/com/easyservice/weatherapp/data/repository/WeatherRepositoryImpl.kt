package com.easyservice.weatherapp.data.repository

import com.easyservice.weatherapp.data.remote.WeatherAPI
import com.easyservice.weatherapp.domain.weather.WeatherInfo
import com.easyservice.weatherapp.domain.util.toWeatherInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherAPI
): WeatherRepository {

    override suspend fun getWeatherData(lat: Double, long: Double): Flow<Result<WeatherInfo>> {
        return flow {
            emit(Result.success(api.getWeatherData(lat, long).toWeatherInfo()))
        }.catch {
            it.printStackTrace()
            emit(Result.failure(it))
        }
    }
}