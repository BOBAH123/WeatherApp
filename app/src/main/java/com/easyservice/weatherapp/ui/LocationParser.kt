package com.easyservice.weatherapp.ui

import android.content.Context
import android.location.Address
import android.location.Geocoder
import java.io.IOException
import javax.inject.Inject

class LocationParser @Inject constructor() {
    fun getLocationFromAddress(context: Context, strAddress: String?): GeoPoint? {
        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: GeoPoint? = null
        try {
            address = coder.getFromLocationName(strAddress!!, 5)
            if (address == null) {
                return null
            }
            val location = address[0]
            location.latitude
            location.longitude
            p1 = GeoPoint(
                (location.latitude),
                (location.longitude)
            )
            return p1
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}

class GeoPoint (
    val lat: Double,
    val long: Double
)