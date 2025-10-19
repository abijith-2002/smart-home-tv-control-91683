package com.smarthome.tv.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.smarthome.tv.ui.components.DeviceCard
import com.smarthome.tv.viewmodel.HomeViewModel

/**
 * PUBLIC_INTERFACE
 * HomeScreen renders a simplified list of devices to avoid inline layout composables.
 */
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val devices = viewModel.devices

    // Title
    Text(
        text = "Devices (${devices.size})",
        style = MaterialTheme.typography.headlineLarge
    )

    // Simple sequential rendering of device cards without Lazy grids or Rows/Columns.
    for (d in devices) {
        DeviceCard(
            device = d,
            onToggle = { viewModel.toggle(it.id) }
        )
    }
}
