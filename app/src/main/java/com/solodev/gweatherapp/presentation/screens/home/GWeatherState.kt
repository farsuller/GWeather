package com.solodev.gweatherapp.presentation.screens.home

import com.solodev.gweatherapp.domain.model.Weather

data class GWeatherState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val weather: Weather? = null
    )


