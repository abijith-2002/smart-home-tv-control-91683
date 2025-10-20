package com.smarthome.tv

import android.os.Bundle
import android.view.View
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

    // Setup sidebar navigation with click handlers and focus effects
    private fun setupNavigation() {
        // Shared focus change listener to scale and improve affordance
        val focusListener = View.OnFocusChangeListener { v: View, hasFocus: Boolean ->
            val scale = if (hasFocus) 1.08f else 1.0f
            v.animate().scaleX(scale).scaleY(scale).setDuration(120).start()
        }

        navHome.onFocusChangeListener = focusListener
        navActivity.onFocusChangeListener = focusListener
        navSettings.onFocusChangeListener = focusListener

        // Click handlers load fragments and update a11y announcement context
        navHome.setOnClickListener {
            loadFragment(HomeFragment())
            // PUBLIC_INTERFACE
            /** Update accessibility announcement for focused navigation item. */
            navHome.contentDescription = getString(R.string.devices)
        }

        navActivity.setOnClickListener {
            loadFragment(ActivityFragment())
            navActivity.contentDescription = getString(R.string.activity)
        }

        navSettings.setOnClickListener {
            loadFragment(SettingsFragment())
            navSettings.contentDescription = getString(R.string.settings)
        }
    }

    // Load fragment into main content area
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_content, fragment)
            .commit()
    }
}
