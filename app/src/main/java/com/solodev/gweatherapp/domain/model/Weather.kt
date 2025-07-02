package com.solodev.gweatherapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val base: String? = null,
    val clouds: Clouds? = null,
    val cod: Int? = null,

    @SerialName("coord")
    val coordinates: Coordinates? = null,
    val dt: Int? = null,

    val main: Main? = null,
    val name: String? = null,
    val sys: Sys? = null,
    val timezone: Int? = null,
    val visibility: Int? = null,

    @SerialName("weather")
    val weatherItem: List<WeatherItem>? = null,

    val wind: Wind? = null,

    val id: Int = 0,
)

@Serializable
data class Clouds(
    val all: Int? = null,
)

@Serializable
data class Coordinates(
    val lat: Double? = null,
    val lon: Double? = null,
)

@Serializable
data class Main(

    @SerialName("feels_like")
    val feelsLike: Double? = null,

    @SerialName("grnd_level")
    val groundLevel: Int? = null,

    val humidity: Int? = null,
    val pressure: Int? = null,

    @SerialName("sea_level")
    val seaLevel: Int? = null,
    val temp: Double? = null,

    @SerialName("temp_max")
    val tempMax: Double? = null,

    @SerialName("temp_min")
    val tempMin: Double? = null,
)

@Serializable
data class Sys(
    val country: String? = null,
    val sunrise: Int? = null,
    val sunset: Int? = null,
)

@Serializable
data class WeatherItem(
    val description: String? = null,
    val icon: String? = null,
    val id: Int? = null,
    val main: String? = null,
)

@Serializable
data class Wind(
    val deg: Int? = null,
    val gust: Double? = null,
    val speed: Double? = null,
)