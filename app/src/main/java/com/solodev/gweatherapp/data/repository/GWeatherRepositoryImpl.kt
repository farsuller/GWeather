package com.solodev.gweatherapp.data.repository

import com.solodev.gweatherapp.data.RequestState
import com.solodev.gweatherapp.data.remote.WeatherApi
import com.solodev.gweatherapp.domain.domain.GWeatherRepository
import com.solodev.gweatherapp.domain.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GWeatherRepositoryImpl(
    private val weatherApi: WeatherApi
) : GWeatherRepository {
    override fun getWeather(
        latitude: String,
        longitude: String
    ): Flow<RequestState<Weather>>  = flow {
        try {
            val weather = weatherApi.getWeather(latitude = latitude, longitude = longitude)
            emit(RequestState.Success(weather))
        } catch (e: Exception){
            emit(RequestState.Error("${e.message}"))
        }
    }.flowOn(Dispatchers.IO)
}