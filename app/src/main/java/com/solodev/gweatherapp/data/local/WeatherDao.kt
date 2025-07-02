package com.solodev.gweatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.solodev.gweatherapp.domain.model.WeatherEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM WeatherEntity ORDER BY timestamp DESC")
    fun getWeatherHistory(): List<WeatherEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherEntity)
}
