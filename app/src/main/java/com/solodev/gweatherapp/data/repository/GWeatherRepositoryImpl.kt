package com.solodev.gweatherapp.data.repository

import com.solodev.gweatherapp.data.RequestState
import com.solodev.gweatherapp.data.local.WeatherDao
import com.solodev.gweatherapp.data.remote.WeatherApi
import com.solodev.gweatherapp.domain.domain.GWeatherRepository
import com.solodev.gweatherapp.domain.model.Weather
import com.solodev.gweatherapp.domain.model.WeatherEntity
import com.solodev.gweatherapp.domain.model.toDomain
import com.solodev.gweatherapp.domain.model.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class GWeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val weatherDao: WeatherDao
) : GWeatherRepository {
    override fun getWeather(
        latitude: String,
        longitude: String
    ): Flow<RequestState<Weather>>  = flow {
        try {
            val weather = weatherApi.getWeather(latitude = latitude, longitude = longitude)
            weatherDao.insertWeather(weather.toEntity().also { println("Weather inserted $it") })
            emit(RequestState.Success(weather))
        } catch (e: Exception){
            emit(RequestState.Error("${e.message}"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getWeatherHistory(): Flow<List<Weather>> = flow {
        emit(weatherDao.getWeatherHistory().map { it.toDomain().also { println("Weather get $it") } })
    }.flowOn(Dispatchers.IO)
}