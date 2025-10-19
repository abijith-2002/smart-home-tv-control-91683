package com.smarthome.tv.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * PUBLIC_INTERFACE
 * ActivityScreen renders a simple heading placeholder, simplified to avoid inline Box.
 */
@Composable
fun ActivityScreen() {
    Text(text = "Activity", style = MaterialTheme.typography.headlineLarge)
}
