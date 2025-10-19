package com.smarthome.tv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import com.smarthome.tv.ui.theme.SmartHomeTVTheme
import com.smarthome.tv.viewmodel.HomeViewModel

/**
 * PUBLIC_INTERFACE
 * MainActivity is the entry point for the Smart Home Android TV app.
 * It hosts the Compose content, sets the app-wide theme, manages sidebar
 * tab navigation state (hoisted at Activity scope), and provides the HomeViewModel.
 */
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()

    // Hoist tab state to Activity scope to avoid creating state during composition
    private val selectedTabState = mutableStateOf(Tab.Home)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartHomeTVTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppScaffold(
                        homeViewModel = homeViewModel,
                        selectedTab = selectedTabState.value,
                        onTabChange = { selectedTabState.value = it }
                    )
                }
            }
        }
    }
}

enum class Tab { Home, Activity, Settings }


