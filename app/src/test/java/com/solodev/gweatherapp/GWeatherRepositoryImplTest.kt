package com.solodev.gweatherapp

import com.solodev.gweatherapp.data.RequestState
import com.solodev.gweatherapp.data.local.WeatherDao
import com.solodev.gweatherapp.data.remote.WeatherApi
import com.solodev.gweatherapp.data.repository.GWeatherRepositoryImpl
import com.solodev.gweatherapp.domain.model.Coordinates
import com.solodev.gweatherapp.domain.model.Main
import com.solodev.gweatherapp.domain.model.Sys
import com.solodev.gweatherapp.domain.model.Weather
import com.solodev.gweatherapp.domain.model.WeatherItem
import com.solodev.gweatherapp.domain.model.Wind
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*


@OptIn(ExperimentalCoroutinesApi::class)
class GWeatherRepositoryImplTest {

    private val weatherApi = mock<WeatherApi>()
    private val weatherDao = mock<WeatherDao>()
    private lateinit var repository: GWeatherRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = GWeatherRepositoryImpl(weatherApi, weatherDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getWeather emits Success when API returns data`() = runTest {

        val fakeWeather = Weather(
            id = 1,
            name = "Manila",
            weatherItem = listOf(WeatherItem("Clear", "clear sky")),
            main = Main(301.5, 80, 1008),
            wind = Wind(speed = 1.0),
            sys = Sys(sunrise = 1625088000, sunset =  1625134800),
            dt = 1625100000,
            timezone = 28800,
            coordinates = Coordinates(14.6, 120.9)
        )

        `when`(weatherApi.getWeather("14.6", "120.9")).thenReturn(fakeWeather)


        val result = repository.getWeather("14.6", "120.9").first()

        assertTrue(result is RequestState.Success)
        val data = (result as RequestState.Success).result
        assertEquals("Manila", data.name)

    }

    @Test
    fun `getWeather emits Error when API throws exception`() = runTest {

        `when`(weatherApi.getWeather(anyString(), anyString()))
            .thenThrow(RuntimeException("API failed"))

        val result = repository.getWeather("0", "0").first()

        assertTrue(result is RequestState.Error)
        assertEquals("API failed", (result as RequestState.Error).message)
    }
}