package com.solodev.gweatherapp.util

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import coil3.ImageLoader
import coil3.gif.AnimatedImageDecoder
import coil3.gif.GifDecoder
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

fun provideImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(AnimatedImageDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
}

fun kelvinToCelsius(kelvin: Double?): Double {
    val kelvin = kelvin ?: 0.0
    return kelvin - 273.15
}

fun convertUnixToReadableTime(unixTimestamp: Int?, timezoneOffset: Int?): String {
    val unixTimestamp = unixTimestamp ?: 0
    val timezoneOffset = timezoneOffset ?: 0

    val instant = Instant.ofEpochSecond(unixTimestamp.toLong())
    val zoneId = ZoneId.ofOffset("UTC", ZoneOffset.ofTotalSeconds(timezoneOffset))
    val formatter = DateTimeFormatter.ofPattern("hh:mm a").withZone(zoneId)
    return formatter.format(instant)
}

fun formatUnixToLocalTime(unixSeconds: Long?, timezoneOffsetInSeconds: Int?): String {
    val unixSeconds = unixSeconds ?: 0
    val timezoneOffsetInSeconds = timezoneOffsetInSeconds ?: 0

    val instant = Instant.ofEpochSecond(unixSeconds)
    val zoneOffset = ZoneOffset.ofTotalSeconds(timezoneOffsetInSeconds)
    val zonedDateTime = instant.atZone(zoneOffset)

    val formatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy –\nhh:mm a", Locale.getDefault())
    return zonedDateTime.format(formatter)
}

fun isNightTime(unixSeconds: Long?, timezoneOffsetInSeconds: Int?): Boolean {
    val unixSeconds = unixSeconds ?: 0
    val timezoneOffsetInSeconds = timezoneOffsetInSeconds ?: 0

    val instant = Instant.ofEpochSecond(unixSeconds)
    val zoneOffset = ZoneOffset.ofTotalSeconds(timezoneOffsetInSeconds)
    val localTime = instant.atZone(zoneOffset).toLocalTime()
    val hour = localTime.hour
    return hour < 6 || hour >= 18 // 6 PM to 6 AM is considered night
}

fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$"
    return email.matches(emailRegex.toRegex())
}
