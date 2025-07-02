package com.solodev.gweatherapp.presentation.screens.home.component

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Cyclone
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.solodev.gweatherapp.R
import com.solodev.gweatherapp.presentation.screens.home.GWeatherState
import com.solodev.gweatherapp.ui.theme.Gray
import com.solodev.gweatherapp.util.convertUnixToReadableTime
import com.solodev.gweatherapp.util.formatUnixToLocalTime
import com.solodev.gweatherapp.util.isNightTime
import com.solodev.gweatherapp.util.kelvinToCelsius
import com.solodev.gweatherapp.util.provideImageLoader

@SuppressLint("DefaultLocale")
@Composable
fun WeatherNowCard(gWeatherState: GWeatherState) {
    val context = LocalContext.current
    val imageLoader = remember { provideImageLoader(context) }

    val weatherData = gWeatherState.weather

    val weather = weatherData?.weatherItem?.firstOrNull()

    val weatherIconNow = remember(gWeatherState) {
        val isNight = isNightTime(
            unixSeconds = System.currentTimeMillis() / 1000,
            timezoneOffsetInSeconds = gWeatherState.weather?.timezone,
        )

        when {
            isNight -> R.drawable.night2
            weather?.main == "Clear" -> R.drawable.sun
            weather?.main == "Rain" || weather?.main == "Moderate Rain" -> R.drawable.rain
            weather?.main == "Clouds" -> R.drawable.cloudy
            weather?.main == "Partially cloudy" -> R.drawable.sun
            weather?.main == "Thunder" -> R.drawable.thunder
            else -> R.drawable.sun
        }
    }

    val painter = rememberAsyncImagePainter(
        model = weatherIconNow,
        imageLoader = imageLoader,
    )

    val localTimeString = formatUnixToLocalTime(weatherData?.dt?.toLong(), weatherData?.timezone)

    val sunriseText = convertUnixToReadableTime(weatherData?.sys?.sunrise, weatherData?.timezone)
    val sunsetText = convertUnixToReadableTime(weatherData?.sys?.sunset, weatherData?.timezone)

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .padding(top = 10.dp),
        shape = RoundedCornerShape(13.dp),
        colors = CardDefaults.cardColors(containerColor = Gray),
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(150.dp),
                    painter = painter,
                    contentDescription = null,
                )

                val formattedTemp = String.format("%.2f", kelvinToCelsius(weatherData?.main?.temp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Now",
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.labelMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                            fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                        ),
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Text(
                        text = "$formattedTemp°",
                        fontSize = 60.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }


                Text(
                    text = "${weatherData?.weatherItem?.firstOrNull()?.main}",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = localTimeString,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 14.sp,
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = weatherData?.weatherItem?.firstOrNull()?.description
                            ?.split(" ")
                            ?.joinToString(" ")
                            { word ->
                                word.replaceFirstChar { it.uppercase() }
                            } ?: "",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    IconTextItem(
                        text = "${weatherData?.main?.humidity}%",
                        subtitle = "Humidity",
                        icon = Icons.Default.WaterDrop
                    )

                    IconTextItem(
                        text = "${weatherData?.main?.pressure} hPa",
                        subtitle = "Pressure",
                        icon = Icons.Default.Cyclone
                    )

                    IconTextItem(
                        text = "${weatherData?.wind?.speed} m/s",
                        subtitle = "Wind",
                        icon = Icons.Default.Air
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {

                    IconTextItem(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = sunriseText,
                        subtitle = "Sunrise",
                        icon = Icons.Default.WbSunny
                    )

                    IconTextItem(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = sunsetText,
                        subtitle = "Sunset",
                        icon = Icons.Default.WbTwilight
                    )

                }
            }

        }
    }
}

@Composable
fun IconTextItem(modifier: Modifier = Modifier, text: String, subtitle: String, icon: ImageVector) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
        )

        Row {
            Icon(
                imageVector = icon,
                contentDescription = subtitle,
                tint = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = subtitle,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
            )
        }
    }
}
