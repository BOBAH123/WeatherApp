package com.easyservice.weatherapp.domain.location

import android.location.Location

interface CurrentLocationTracker {
    suspend fun getCurrentLocation(): Location?
}