package com.solodev.gweatherapp.domain.usecase

import com.solodev.gweatherapp.data.RequestState
import com.solodev.gweatherapp.domain.domain.GWeatherRepository
import com.solodev.gweatherapp.domain.model.Weather
import kotlinx.coroutines.flow.Flow

class GetWeather(
    private val repository: GWeatherRepository
) {
    operator fun invoke(
        latitude: String,
        longitude: String
    ): Flow<RequestState<Weather>> = repository.getWeather(latitude, longitude)
}