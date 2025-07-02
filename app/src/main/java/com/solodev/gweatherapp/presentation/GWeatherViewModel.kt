package com.solodev.gweatherapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solodev.gweatherapp.data.RequestState
import com.solodev.gweatherapp.domain.model.Weather
import com.solodev.gweatherapp.domain.usecase.WeatherUseCase
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

    private val _weatherState = MutableStateFlow(GWeatherState())
    val weatherState : StateFlow<GWeatherState> = _weatherState.asStateFlow()

    fun requestApi(){
        getWeather(14.7070,121.1052)
    }


    private fun getWeather(latitude: Double, longitude: Double) = viewModelScope.launch {
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