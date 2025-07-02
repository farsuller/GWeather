package com.solodev.gweatherapp.domain.domain

import com.solodev.gweatherapp.data.RequestState
import com.solodev.gweatherapp.domain.model.Weather
import kotlinx.coroutines.flow.Flow

interface GWeatherRepository {
    fun getWeather(latitude: String, longitude: String): Flow<RequestState<Weather>>
}