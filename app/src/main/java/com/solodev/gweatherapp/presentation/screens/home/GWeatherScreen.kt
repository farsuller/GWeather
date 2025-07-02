package com.solodev.gweatherapp.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.solodev.gweatherapp.R
import com.solodev.gweatherapp.presentation.screens.home.component.WeatherHistoryCard
import com.solodev.gweatherapp.presentation.screens.home.component.WeatherNowCard
import com.solodev.gweatherapp.util.Tabs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GWeatherScreen(
    gWeatherState: GWeatherState,
    onSignOutClick: () -> Unit,
) {
    var selectedTabIndex by remember { mutableIntStateOf(Tabs.HOME.ordinal) }
    val tabs = Tabs.entries

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "GWeather") },
                actions = {
                    IconButton(
                        onClick = onSignOutClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout",
                            tint = Color.Black
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding()),
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = tab.title, color = Color.Black) },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                                tint = Color.Black
                            )
                        },
                    )
                }
            }

            when (tabs[selectedTabIndex]) {
                Tabs.HOME -> {
                    when {
                        gWeatherState.isLoading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        gWeatherState.errorMessage != null -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Column(modifier = Modifier.matchParentSize()) {
                                    Text(text = "Error: ${gWeatherState.errorMessage}")
                                }
                            }
                        }

                        gWeatherState.weather != null -> {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp),
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Location",
                                        tint = Color.Black
                                    )

                                    Text(text = "${gWeatherState.weather.name}, ${gWeatherState.weather.sys?.country}")
                                }

                                WeatherNowCard(gWeatherState = gWeatherState)

                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }
                }

                Tabs.HISTORY -> {
                    when {
                        gWeatherState.weatherHistory != null -> {

                            val weather = gWeatherState.weatherHistory

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(10.dp),
                            ) {
                                itemsIndexed(weather) { index, weather ->
                                    WeatherHistoryCard(
                                        weatherData = weather,
                                        iconImage = when(weather.weatherItem?.firstOrNull()?.main) {
                                            "Clear" -> R.drawable.sun
                                            "Rain", "Moderate Rain" -> R.drawable.rain
                                            "Clouds" -> R.drawable.cloudy
                                            "Partially cloudy" -> R.drawable.sun
                                            "Thunder" -> R.drawable.thunder
                                            else -> R.drawable.sun
                                        },
                                        isHistory = true,
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }

    }


}