package com.solodev.gweatherapp.domain.usecase

import com.solodev.gweatherapp.domain.domain.GWeatherRepository
import com.solodev.gweatherapp.domain.model.Weather
import kotlinx.coroutines.flow.Flow

class GetWeathers(
    private val repository: GWeatherRepository,
) {
    operator fun invoke() : Flow<List<Weather>> = repository.getWeatherHistory()
}
