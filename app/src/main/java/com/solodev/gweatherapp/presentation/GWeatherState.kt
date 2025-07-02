package com.solodev.gweatherapp.presentation

import com.solodev.gweatherapp.domain.model.Weather

data class GWeatherState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val weather: Weather = Weather()
    )


