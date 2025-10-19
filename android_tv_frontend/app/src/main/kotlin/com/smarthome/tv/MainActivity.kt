package com.smarthome.tv

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

// PUBLIC_INTERFACE
/**
 * Main activity for Smart Home TV app with sidebar navigation and fragment management.
 * Supports TV remote DPAD navigation between sidebar items and content.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var navHome: ImageView
    private lateinit var navActivity: ImageView
    private lateinit var navSettings: ImageView
    private lateinit var mainContent: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        navHome = findViewById(R.id.nav_home)
        navActivity = findViewById(R.id.nav_activity)
        navSettings = findViewById(R.id.nav_settings)
        mainContent = findViewById(R.id.main_content)

        // Setup navigation click listeners
        setupNavigation()

        // Load home fragment by default
        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
            navHome.requestFocus()
        }
    }

    // Setup sidebar navigation with click handlers
    private fun setupNavigation() {
        navHome.setOnClickListener {
            loadFragment(HomeFragment())
        }

        navActivity.setOnClickListener {
            loadFragment(ActivityFragment())
        }

        navSettings.setOnClickListener {
            loadFragment(SettingsFragment())
        }
    }

    // Load fragment into main content area
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_content, fragment)
            .commit()
    }
}
