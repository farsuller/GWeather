package com.solodev.gweatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.solodev.gweatherapp.domain.model.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}