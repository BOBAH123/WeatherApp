package com.easyservice.weatherapp.ui

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.easyservice.weatherapp.ui.components.FutureWeatherForecast
import com.easyservice.weatherapp.ui.components.SearchBar
import com.easyservice.weatherapp.ui.components.TodayWeatherForecast
import com.easyservice.weatherapp.ui.components.WeatherCard
import com.easyservice.weatherapp.ui.theme.Purple40
import com.easyservice.weatherapp.ui.theme.WeatherAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
        setContent {
            val searchText by viewModel.searchText.collectAsState()
            WeatherAppTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Purple40)
                    ) {
                        item {
                            SearchBar(
                                searchText = searchText,
                                onSearchTextChange = viewModel::onSearchTextChange,
                                onSearchSubmit = viewModel::getWeatherInConcretePlace
                            )
                        }
                        item {
                            WeatherCard(
                                state = viewModel.state,
                                backgroundColor = Color.DarkGray
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            TodayWeatherForecast(state = viewModel.state)
                        }
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                            FutureWeatherForecast(state = viewModel.state)
                        }
                    }
                    if (viewModel.state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    viewModel.state.error?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}
