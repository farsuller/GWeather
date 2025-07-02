package com.solodev.gweatherapp.presentation.screens.splash

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.solodev.gweatherapp.R
import com.solodev.gweatherapp.domain.model.Location
import com.solodev.gweatherapp.presentation.GWeatherViewModel
import com.solodev.gweatherapp.presentation.MainActivity
import com.solodev.gweatherapp.routes.LoginRoute
import com.solodev.gweatherapp.ui.theme.ChromeYellow
import com.solodev.gweatherapp.util.LocationUtils
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun GSplashScreen(navController: NavController) {
    val context = LocalContext.current
    val locationUtils = remember { LocationUtils(context) }

    LaunchedEffect(Unit) {
        delay(3000)
        navController.navigate(LoginRoute)
    }

    DisplayLocationPopUp(locationUtils = locationUtils)
}

@Composable
fun DisplayLocationPopUp(locationUtils: LocationUtils) {
    val context = LocalContext.current
    val viewModel = koinViewModel<GWeatherViewModel>()

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->

            val hasPermission = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                    permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true

            if (hasPermission) {
                locationUtils.requestLocationUpdates { lat, lon ->
                    viewModel.updateLocation(Location(lat, lon))
                }
            } else {
                val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                    context as MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                )

                if (rationaleRequired) {
                    Toast.makeText(context, "Location Permission Required", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        context,
                        "Enable Location Permission From Settings",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        },
    )

    LaunchedEffect(Unit) {
        if (locationUtils.hasLocationPermission(context)) {
            locationUtils.requestLocationUpdates { latitude, longitude ->
                viewModel.updateLocation(Location(latitude, longitude))
            }
        } else {
            requestPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ),
            )
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            locationUtils.stopLocationUpdates()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(5.dp)
                .size(140.dp)
                ,
            contentAlignment = Alignment.Center
        ){
            Icon(
                modifier = Modifier.size(250.dp),
                painter = painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Logo Image",
                tint = ChromeYellow,
            )
        }
    }
}
