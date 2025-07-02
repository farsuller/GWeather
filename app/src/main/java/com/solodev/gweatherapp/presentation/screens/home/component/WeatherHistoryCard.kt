package com.solodev.gweatherapp.presentation.screens.home.component

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.solodev.gweatherapp.domain.model.Weather
import com.solodev.gweatherapp.ui.theme.Gray
import com.solodev.gweatherapp.util.formatUnixToLocalTime
import com.solodev.gweatherapp.util.kelvinToCelsius
import com.solodev.gweatherapp.util.provideImageLoader

@SuppressLint("DefaultLocale")
@Composable
fun WeatherHistoryCard(
    weatherData: Weather,
    @DrawableRes iconImage: Int,
    isHistory: Boolean = false,
) {
    val context = LocalContext.current
    val imageLoader = remember { provideImageLoader(context) }

    val painter = rememberAsyncImagePainter(
        model = iconImage,
        imageLoader = imageLoader,
    )

    val localTimeString = formatUnixToLocalTime(
        weatherData.dt?.toLong(),
        weatherData.timezone
    )

    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
        ),
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp)
                            .padding(10.dp),
                        painter = painter,
                        contentDescription = null,
                    )

                    if (!isHistory) {
                        Text(
                            text = "Now",
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                fontWeight = FontWeight.Bold,
                                fontFamily = MaterialTheme.typography.titleLarge.fontFamily,
                            ),
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }

                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = localTimeString,
                        fontSize = MaterialTheme.typography.labelMedium.fontSize,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 14.sp,
                        textAlign = TextAlign.Center,
                    )
                }

                val weatherMain = weatherData.weatherItem?.firstOrNull()
                Column(
                    modifier = Modifier.padding(10.dp),
                ) {
                    val formattedTemp =
                        String.format("%.2f", kelvinToCelsius(weatherData.main?.temp))
                    Text(
                        text = "Temperature: $formattedTemp°C",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Text(
                        text = "Weather: ${weatherMain?.main}",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Text(
                        text = "Description: ${
                            weatherMain?.description
                                ?.split(" ")
                                ?.joinToString(" ")
                                { word ->
                                    word.replaceFirstChar { it.uppercase() }
                                } ?: ""
                        }",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        color = MaterialTheme.colorScheme.onSurface,
                    )

                    Text(
                        text = "Humidity: ${weatherData.main?.humidity}%",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    )
                    Text(
                        text = "Wind Speed: ${weatherData.wind?.speed} m/s",
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    )
                }
            }
        }
    }
}
