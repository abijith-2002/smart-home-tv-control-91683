package com.smarthome.tv.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * PUBLIC_INTERFACE
 * SettingsScreen renders a simple heading placeholder. Simplified to avoid inline Box.
 */
@Composable
fun SettingsScreen() {
    Text(text = "Settings", style = MaterialTheme.typography.headlineLarge)
}
