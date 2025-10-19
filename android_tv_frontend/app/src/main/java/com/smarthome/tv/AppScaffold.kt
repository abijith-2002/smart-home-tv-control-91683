package com.smarthome.tv

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.smarthome.tv.ui.components.Sidebar
import com.smarthome.tv.ui.screens.ActivityScreen
import com.smarthome.tv.ui.screens.HomeScreen
import com.smarthome.tv.ui.screens.SettingsScreen
import com.smarthome.tv.viewmodel.HomeViewModel

/**
 * PUBLIC_INTERFACE
 * AppScaffold (temporary minimal version) renders the Sidebar first, then the main screen.
 * It avoids inline layout primitives (Row/Box) to work around current IR inlining issues.
 */
@Composable
fun AppScaffold(
    homeViewModel: HomeViewModel,
    selectedTab: Tab,
    onTabChange: (Tab) -> Unit
) {
    // Sidebar at top (sequentially rendered)
    Sidebar(
        selected = selectedTab,
        onSelect = onTabChange
    )

    // Main area: render selected screen
    when (selectedTab) {
        Tab.Home -> HomeScreen(viewModel = homeViewModel)
        Tab.Activity -> ActivityScreen()
        Tab.Settings -> SettingsScreen()
    }
}
