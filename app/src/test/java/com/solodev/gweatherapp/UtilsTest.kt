package com.solodev.gweatherapp

import com.solodev.gweatherapp.util.convertUnixToReadableTime
import com.solodev.gweatherapp.util.formatUnixToLocalTime
import com.solodev.gweatherapp.util.isNightTime
import com.solodev.gweatherapp.util.isValidEmail
import com.solodev.gweatherapp.util.kelvinToCelsius
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class UtilsTest {

    @Test
    fun `kelvinToCelsius returns correct Celsius value`() {
        assertEquals(0.0, kelvinToCelsius(273.15), 0.01)
        assertEquals(26.85, kelvinToCelsius(300.0), 0.01)
        assertEquals(-273.15, kelvinToCelsius(0.0), 0.01)
        assertEquals(-273.15, kelvinToCelsius(null), 0.01)
    }

    @Test
    fun `convertUnixToReadableTime returns expected format`() {
        val timestamp = 1625074800  // July 1, 2021 07:00:00 UTC
        val offset = 3600           // +01:00
        val formatted = convertUnixToReadableTime(timestamp, offset)
        assertTrue(formatted.matches(Regex("\\d{2}:\\d{2} [AP]M")))
    }

    @Test
    fun `formatUnixToLocalTime returns expected formatted string`() {
        val timestamp = 1625074800L // July 1, 2021 07:00:00 UTC
        val offset = 28800          // +08:00
        val formatted = formatUnixToLocalTime(timestamp, offset)
        assertTrue(formatted.contains("2021"))
        assertTrue(formatted.contains("–"))
        assertTrue(formatted.contains("AM") || formatted.contains("PM"))
    }

    @Test
    fun `isNightTime correctly determines night and day`() {
        val morningTimestamp = 1625035200L // 6:00 AM UTC
        val nightTimestamp = 1625078400L   // 6:00 PM UTC
        val offset = 0                     // UTC

        assertFalse(isNightTime(morningTimestamp, offset))
        assertTrue(isNightTime(nightTimestamp, offset))
    }

    @Test
    fun `isValidEmail validates correct email formats`() {
        assertTrue(isValidEmail("test@example.com"))
        assertTrue(isValidEmail("user.name+tag@domain.co.uk"))

        assertFalse(isValidEmail("testexample.com"))
        assertFalse(isValidEmail("test@.com"))
        assertFalse(isValidEmail("test@com"))
        assertFalse(isValidEmail("@example.com"))
    }
}