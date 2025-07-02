package com.solodev.gweatherapp.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Long = 0,

    val id : Int,
    val name : String,
    val weather : String,
    val description : String,
    val latitude : Double,
    val longitude : Double,
    val temperature : Double,
    val humidity : Int,
    val wind : Double,
    val pressure : Int,
    val sunrise : Int,
    val sunset : Int,
    val dt : Int,
    val timezone : Int,
    val timestamp: Long = System.currentTimeMillis(),
)

fun Weather.toEntity() = WeatherEntity(
    id = id,
    name = name.orEmpty(),
    weather = weatherItem?.firstOrNull()?.main.orEmpty(),
    description = weatherItem?.firstOrNull()?.description.orEmpty(),
    temperature = main?.temp ?: 0.0,
    humidity = main?.humidity ?: 0,
    wind = wind?.speed ?: 0.0,
    pressure = main?.pressure ?: 0,
    sunrise = sys?.sunrise ?: 0,
    sunset = sys?.sunset ?: 0,
    dt = dt ?: 0,
    timezone = timezone ?: 0,
    latitude = coordinates?.lat ?: 0.0,
    longitude = coordinates?.lon ?: 0.0,
)

fun WeatherEntity.toDomain() = Weather(
    id = id,
    name = name,
    weatherItem = listOf(
        WeatherItem(
            main = weather,
            description = description
        )
    ),
    main = Main(
        temp = temperature,
        humidity = humidity,
        pressure = pressure
    ),
    wind = Wind(
        speed = wind
    ),
    sys = Sys(
        sunrise = sunrise,
        sunset = sunset
    ),
    dt = dt,
    timezone = timezone,
    coordinates = Coordinates(
        lat = latitude,
        lon = longitude
    )
)

