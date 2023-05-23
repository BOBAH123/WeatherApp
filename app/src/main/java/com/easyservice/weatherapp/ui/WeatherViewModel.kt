package com.easyservice.weatherapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easyservice.weatherapp.MainApplication
import com.easyservice.weatherapp.data.repository.WeatherRepository
import com.easyservice.weatherapp.domain.location.CurrentLocationTracker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: CurrentLocationTracker,
    private val locationParser: LocationParser
) : ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText.asStateFlow()

    fun loadWeatherInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                val result = repository.getWeatherData(location.latitude, location.longitude)
                result.collect {
                    when {
                        it.isSuccess -> {
                            state = state.copy(
                                weatherInfo = it.getOrNull(),
                                isLoading = false,
                                error = null
                            )
                        }

                        it.isFailure -> {
                            state = state.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = "Something went wrong"
                            )
                        }
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }

    fun getWeatherInConcretePlace() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            if (_searchText.value.isNotEmpty()) {
                locationParser.getLocationFromAddress(
                    MainApplication.applicationContext(),
                    _searchText.value
                )?.let {
                    repository.getWeatherData(it.lat, it.long).collect { result ->
                        when {
                            result.isSuccess -> {
                                state = state.copy(
                                    weatherInfo = result.getOrNull(),
                                    isLoading = false,
                                    error = null
                                )
                            }

                            result.isFailure -> {
                                state = state.copy(
                                    weatherInfo = null,
                                    isLoading = false,
                                    error = "Something went wrong"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }
}