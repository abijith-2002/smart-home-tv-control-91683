package com.smarthome.tv.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import com.smarthome.tv.viewmodel.DeviceItem

/**
 * PUBLIC_INTERFACE
 * DeviceCard (temporary minimal version).
 * This simplified version avoids inline Compose layout primitives (Box/Row/Column/Spacer)
 * to work around current Kotlin IR inlining build failures. It still handles DPAD_CENTER
 * to toggle the device via onToggle and shows the device state in a single line.
 *
 * Once the build toolchain is stable, replace this with the richer card layout.
 */
@Composable
fun DeviceCard(
    device: DeviceItem,
    onToggle: (DeviceItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val line = buildString {
        append(device.name)
        append(" — ")
        append(if (device.isOn) "On" else "Off")
        append("  (OK to toggle)")
    }

    // Note: We attach onKeyEvent to the modifier so DPAD_CENTER toggles state.
    // Using only Text here avoids inline Box/Row/Column usage that triggers IR issues.
    Text(
        text = line,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.onKeyEvent { event ->
            val native = event.nativeKeyEvent
            if (native.keyCode == android.view.KeyEvent.KEYCODE_DPAD_CENTER &&
                native.action == android.view.KeyEvent.ACTION_UP
            ) {
                onToggle(device)
                true
            } else {
                false
            }
        }
    )
}
