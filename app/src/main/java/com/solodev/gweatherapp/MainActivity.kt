package com.solodev.gweatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.solodev.gweatherapp.presentation.GWeatherScreen
import com.solodev.gweatherapp.presentation.GWeatherViewModel
import com.solodev.gweatherapp.ui.theme.GWeatherAppTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GWeatherAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val viewModel: GWeatherViewModel = koinViewModel()
                    val gWeatherState by viewModel.weatherState.collectAsStateWithLifecycle()

                    LaunchedEffect(Unit) {
                        viewModel.requestApi()
                    }

                    GWeatherScreen(
                        gWeatherState = gWeatherState
                    )
                }
            }
        }
    }
}

