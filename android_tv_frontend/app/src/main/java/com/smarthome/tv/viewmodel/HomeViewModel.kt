package com.smarthome.tv.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import java.util.UUID

/**
 * Device item model stored in memory for UI state.
 */
data class DeviceItem(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val isOn: Boolean
)

/**
 * PUBLIC_INTERFACE
 * HomeViewModel stores device states in-memory and exposes toggle functionality.
 */
class HomeViewModel : ViewModel() {

    // Backing stateful list
    val devices = mutableStateListOf<DeviceItem>().apply {
        addAll(
            listOf(
                DeviceItem(name = "Living Room Light", isOn = true),
                DeviceItem(name = "Living Room Fan", isOn = false),
                DeviceItem(name = "Dining Room Fan", isOn = false),
                DeviceItem(name = "Bedroom 1 Light", isOn = false),
                DeviceItem(name = "Bedroom 1 AC", isOn = true),
            )
        )
    }

    // PUBLIC_INTERFACE
    /** Toggle device by id. */
    fun toggle(id: String) {
        val idx = devices.indexOfFirst { it.id == id }
        if (idx >= 0) {
            val d = devices[idx]
            devices[idx] = d.copy(isOn = !d.isOn)
        }
    }
}
