package com.solodev.gweatherapp.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.CloudQueue
import androidx.compose.ui.graphics.vector.ImageVector

enum class Tabs(val title: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.CloudQueue),
    HISTORY("History", Icons.Default.Cloud),
}
