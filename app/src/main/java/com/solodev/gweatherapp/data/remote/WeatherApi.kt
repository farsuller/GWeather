package com.solodev.gweatherapp.data.remote

import com.solodev.gweatherapp.BuildConfig
import com.solodev.gweatherapp.domain.model.Weather
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class WeatherApi(
private val client: HttpClient
) {
    suspend fun getWeather(
        latitude : String,
        longitude : String,
    ) : Weather {
     return client.get("weather"){
         url{
             parameters.append("lat", latitude)
             parameters.append("lon", longitude)
             parameters.append("appid", BuildConfig.API_KEY)
         }
     }.body()
    }
}