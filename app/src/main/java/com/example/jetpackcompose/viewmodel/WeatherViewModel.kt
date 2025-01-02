package com.example.jetpackcompose.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackcompose.api.WeatherApiService
import com.example.jetpackcompose.data.ForecastItem
import com.example.jetpackcompose.data.WeatherData
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WeatherViewModel : ViewModel() {

    private val _currentWeather = MutableStateFlow<WeatherData?>(null)
    val currentWeather: StateFlow<WeatherData?> = _currentWeather

    private val _forecast = MutableStateFlow<List<ForecastItem>>(emptyList())
    val forecast: StateFlow<List<ForecastItem>> = _forecast

    private val _iconUrl = MutableStateFlow<String?>(null)
    val iconUrl: StateFlow<String?> get() = _iconUrl

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> get() = _errorMessage

    fun fetchWeatherData(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                Log.d("WeatherViewModel", "Fetching weather data for city: $city with API Key: $apiKey")
                val weatherResponse = WeatherApiService.fetchWeather(city, apiKey)
                if (weatherResponse != null) {
                    _currentWeather.value = weatherResponse
                    fetchWeatherIcon(weatherResponse.weather.firstOrNull()?.icon.orEmpty())
                    _errorMessage.value = null
                    Log.d("WeatherViewModel", "Weather data fetched successfully")
                } else {
                    _errorMessage.value = "Failed to fetch weather. Please check your API key or city name."
                    Log.e("WeatherViewModel", "Weather fetch failed")
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.localizedMessage}"
                Log.e("WeatherViewModel", "Error occurred: ${e.localizedMessage}")
            }
        }
    }

    fun fetchForecastData(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                Log.d("WeatherViewModel", "Fetching forecast data for city: $city with API Key: $apiKey")
                val forecastResponse = WeatherApiService.fetchForecast(city, apiKey)
                if (forecastResponse != null) {
                    _forecast.value = forecastResponse.list
                    _errorMessage.value = null
                    Log.d("WeatherViewModel", "Forecast data fetched successfully")
                } else {
                    _errorMessage.value = "Failed to fetch forecast. Please check your API key or city name."
                    Log.e("WeatherViewModel", "Forecast fetch failed")
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred: ${e.localizedMessage}"
                Log.e("WeatherViewModel", "Error occurred: ${e.localizedMessage}")
            }
        }
    }

    private fun fetchWeatherIcon(iconId: String) {
        if (iconId.isNotEmpty()) {
            _iconUrl.value = "https://openweathermap.org/img/wn/$iconId@2x.png"
        }
    }
}
