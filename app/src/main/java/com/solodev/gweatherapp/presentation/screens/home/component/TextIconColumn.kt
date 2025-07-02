package com.solodev.gweatherapp.presentation.screens.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TextIconColumn(
    modifier: Modifier = Modifier,
    label: String,
    timeText: String,
    icon: ImageVector,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = "$label Icon",
            tint = Color.Black,
        )
        Text(text = "$label: $timeText")

    }
}
