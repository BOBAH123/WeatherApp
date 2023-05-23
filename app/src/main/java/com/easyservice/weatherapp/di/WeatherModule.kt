package com.easyservice.weatherapp.di

import android.app.Application
import com.easyservice.weatherapp.data.remote.WeatherAPI
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WeatherModule {
    @Provides
    fun retrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .client(OkHttpClient())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Provides
    fun weatherApi(retrofit: Retrofit) = retrofit.create(WeatherAPI::class.java)

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }
}