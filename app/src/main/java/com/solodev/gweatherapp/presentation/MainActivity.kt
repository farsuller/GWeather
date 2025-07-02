package com.solodev.gweatherapp.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.solodev.gweatherapp.domain.model.Location
import com.solodev.gweatherapp.presentation.screens.home.GWeatherScreen
import com.solodev.gweatherapp.presentation.screens.login.LoginScreen
import com.solodev.gweatherapp.presentation.screens.signup.SignUpScreen
import com.solodev.gweatherapp.presentation.screens.splash.GSplashScreen
import com.solodev.gweatherapp.routes.HomeRoute
import com.solodev.gweatherapp.routes.LoginRoute
import com.solodev.gweatherapp.routes.SignUpRoute
import com.solodev.gweatherapp.routes.SplashRoute
import com.solodev.gweatherapp.ui.theme.GWeatherAppTheme
import com.solodev.gweatherapp.util.LocationUtils
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GWeatherAppTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = SplashRoute) {
                    composable<SplashRoute> {
                        GSplashScreen(navController = navController)
                    }

                    composable<LoginRoute> {
                        LoginScreen(
                            navigateToSignUp = {
                                navController.navigate(SignUpRoute)
                            },
                            navigateToHome = {
                                navController.navigate(HomeRoute)
                            }
                        )
                    }

                    composable<SignUpRoute> {
                        SignUpScreen(
                            navigateToLogin = {
                                navController.navigate(LoginRoute)
                            }
                        )
                    }

                    composable<HomeRoute> {
                        val viewModel: GWeatherViewModel = koinViewModel()
                        val location by viewModel.location
                        val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()
                        //val weatherHistoryState by viewModel.weatherHistoryState.collectAsStateWithLifecycle()

                        val context = LocalContext.current
                        val locationUtils = remember { LocationUtils(context) }

                        LaunchedEffect(location) {
                            location?.let {
                                viewModel.getWeather(
                                    latitude = it.latitude,
                                    longitude = it.longitude,
                                )
                            }
                        }

                        LaunchedEffect(Unit) {
                            if (locationUtils.hasLocationPermission(context)) {
                                locationUtils.requestLocationUpdates { latitude, longitude ->
                                    viewModel.updateLocation(Location(latitude, longitude))
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Location permission not granted",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }

                        GWeatherScreen(
                            gWeatherState = weatherState,
                            onSignOutClick = {
                                navController.navigate(LoginRoute)
                            },
                        )
                    }
                }

            }
        }
    }
}