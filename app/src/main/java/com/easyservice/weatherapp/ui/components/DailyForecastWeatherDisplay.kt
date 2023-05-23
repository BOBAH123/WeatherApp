package com.easyservice.weatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.easyservice.weatherapp.domain.weather.WeatherData
import java.time.format.DateTimeFormatter

@Composable
fun DailyForecastWeatherDisplay(
    weatherData: List<WeatherData>,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    val formattedTime = remember(weatherData) {
        weatherData[0].time.format(
            DateTimeFormatter.ofPattern("d MMM")
        )
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedTime,
            color = Color.LightGray
        )
        Image(
            painter = painterResource(id = weatherData[weatherData.size / 2].weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = "${weatherData.minBy { it.temperatureCelsius }.temperatureCelsius} " +
                    "/ ${weatherData.maxBy { it.temperatureCelsius }.temperatureCelsius}°C",
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}