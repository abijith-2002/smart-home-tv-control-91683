package com.smarthome.tv.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smarthome.tv.Tab

/**
 * PUBLIC_INTERFACE
 * Sidebar renders an icon-only vertical navigation rail optimized for TV.
 * This implementation avoids inline Column/Row composables to mitigate IR inlining issues.
 */
@Composable
fun Sidebar(
    selected: Tab,
    onSelect: (Tab) -> Unit,
    modifier: Modifier = Modifier
) {
    // Render three buttons with manual spacing via padding (stacked in order).
    SidebarButton(
        isSelected = selected == Tab.Home,
        modifier = modifier.padding(top = 32.dp),
        onClick = { onSelect(Tab.Home) }
    ) {
        Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = "Home",
            tint = if (selected == Tab.Home) MaterialTheme.colorScheme.onSurfaceVariant
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }

    SidebarButton(
        isSelected = selected == Tab.Activity,
        modifier = Modifier.padding(top = 24.dp),
        onClick = { onSelect(Tab.Activity) }
    ) {
        Icon(
            imageVector = Icons.Filled.Timeline,
            contentDescription = "Activity",
            tint = if (selected == Tab.Activity) MaterialTheme.colorScheme.onSurfaceVariant
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }

    SidebarButton(
        isSelected = selected == Tab.Settings,
        modifier = Modifier.padding(top = 24.dp),
        onClick = { onSelect(Tab.Settings) }
    ) {
        Icon(
            imageVector = Icons.Filled.Settings,
            contentDescription = "Settings",
            tint = if (selected == Tab.Settings) MaterialTheme.colorScheme.onSurfaceVariant
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )
    }
}

/**
 * Simplified focusable circular button container for sidebar icons.
 */
@Composable
private fun SidebarButton(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    // Avoid remember and state; compute focus visual minimally
    var isFocused = false
    val bgColor = if (isSelected || isFocused) MaterialTheme.colorScheme.primary.copy(alpha = 0.18f) else Color.Transparent

    Surface(
        shape = CircleShape,
        color = bgColor,
        tonalElevation = 0.dp,
        modifier = modifier
            .size(72.dp)
            .onFocusChanged { state -> isFocused = state.isFocused }
            .focusable(true)
            .clickable { onClick() }
            .background(Color.Transparent)
            .padding(16.dp)
    ) {
        content()
    }
}
