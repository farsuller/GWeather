package com.solodev.gweatherapp.di

import androidx.room.Room
import com.solodev.gweatherapp.data.local.AppDatabase
import com.solodev.gweatherapp.data.remote.WeatherApi
import com.solodev.gweatherapp.data.repository.GWeatherRepositoryImpl
import com.solodev.gweatherapp.domain.domain.GWeatherRepository
import com.solodev.gweatherapp.domain.usecase.GetWeather
import com.solodev.gweatherapp.domain.usecase.GetWeathers
import com.solodev.gweatherapp.domain.usecase.WeatherUseCase
import com.solodev.gweatherapp.presentation.GWeatherViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation){
                json(Json{
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }

            install(HttpTimeout){
                requestTimeoutMillis = 15000
                connectTimeoutMillis = 15000
                socketTimeoutMillis = 15000
            }

            defaultRequest {
                url("https://api.openweathermap.org/data/2.5/")
            }
        }
    }

    single {
        Room.databaseBuilder(get(), AppDatabase::class.java, "gweather.db").build()
    }
    single { get<AppDatabase>().weatherDao() }

    single<WeatherApi> { WeatherApi(client = get()) }

    single<GWeatherRepository> { GWeatherRepositoryImpl(weatherApi = get(), weatherDao = get()) }

    single { GetWeather(get()) }

    single { GetWeathers(get()) }

    single {
        WeatherUseCase(getWeather = get(), getWeathers = get())
    }

    factory {
        GWeatherViewModel(weatherUseCase = get())
    }

}