package com.solodev.gweatherapp.presentation


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.gweatherapp.data.RequestState
import com.solodev.gweatherapp.domain.model.Location
import com.solodev.gweatherapp.domain.model.Weather
import com.solodev.gweatherapp.domain.usecase.WeatherUseCase
import com.solodev.gweatherapp.presentation.screens.home.GWeatherState
import kotlinx.coroutines.flow.MutableStateFlow

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GWeatherViewModel(
    private val weatherUseCase: WeatherUseCase
) : ViewModel() {

    private val _location = mutableStateOf<Location?>(null)
    val location: State<Location?> = _location

    private val _weatherState = MutableStateFlow(GWeatherState())
    val weatherState : StateFlow<GWeatherState> = _weatherState.asStateFlow()

    fun updateLocation(location: Location) {
        _location.value = location
    }

    fun getWeather(latitude: Double, longitude: Double) = viewModelScope.launch {
        weatherUseCase
            .getWeather(
                latitude = latitude.toString(),
                longitude = longitude.toString())
            .onStart { _weatherState.update { it.copy(isLoading = true) } }
            .catch { _weatherState.update { it.copy(isLoading = false, errorMessage = it.errorMessage) } }
            .collectLatest { response ->

                when(response){
                    is RequestState.Error -> {
                        _weatherState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = response.message
                            )
                        }
                    }
                    is RequestState.Success<Weather> -> {
                        _weatherState.update {
                            it.copy(
                                isLoading = false,
                                weather = response.result
                            )
                        }
                    }
                }

            }
    }
}