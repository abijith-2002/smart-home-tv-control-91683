package com.smarthome.tv

// PUBLIC_INTERFACE
/**
 * Data class representing a smart home device
 * @property id Unique identifier for the device
 * @property name Display name of the device
 * @property type Type of device (light, fan, ac)
 * @property isOn Current power state of the device
 */
data class Device(
    val id: Int,
    val name: String,
    val type: DeviceType,
    var isOn: Boolean
)

// PUBLIC_INTERFACE
/**
 * Enum representing different types of smart home devices
 */
enum class DeviceType {
    LIGHT,
    FAN,
    AC
}
