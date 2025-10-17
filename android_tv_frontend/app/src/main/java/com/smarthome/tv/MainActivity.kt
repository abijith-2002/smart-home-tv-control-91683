package com.smarthome.tv

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.smarthome.tv.R
import com.smarthome.tv.ui.screens.DashboardFragment
import com.smarthome.tv.ui.screens.SettingsFragment

/**
 * PUBLIC_INTERFACE
 * MainActivity
 * This is the single-activity entry point for the Smart Home TV app.
 * It hosts a persistent left sidebar and a content container showing
 * either the Dashboard or Settings fragment. It is fully navigable via
 * TV remote D-pad with visible focus states and clear traversal between
 * sidebar and content.
 */
class MainActivity : AppCompatActivity() {

    private enum class Section { DASHBOARD, SETTINGS }

    private var currentSection: Section = Section.DASHBOARD

    private lateinit var navDashboard: TextView
    private lateinit var navSettings: TextView
    private lateinit var contentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Force reference to R to ensure correct package binding
        val layoutId = R.layout.activity_main
        setContentView(layoutId)

        navDashboard = findViewById(R.id.navDashboard)
        navSettings = findViewById(R.id.navSettings)
        contentContainer = findViewById(R.id.contentContainer)

        setupSidebar()

        // Only load the initial section once
        if (savedInstanceState == null) {
            showSection(Section.DASHBOARD)
        }

        // Start focus on sidebar Dashboard item for TV
        navDashboard.isFocusable = true
        navDashboard.requestFocus()
    }

    private fun setupSidebar() {
        val focusBg = ContextCompat.getColor(this, R.color.focus_glow)
        val primary = ContextCompat.getColor(this, R.color.op_primary)
        val textColor = ContextCompat.getColor(this, R.color.op_text)

        val highlight: (TextView) -> Unit = { tv ->
            tv.setBackgroundColor(focusBg)
            tv.setTextColor(primary)
        }
        val unhighlight: (TextView) -> Unit = { tv ->
            tv.setBackgroundColor(0x00000000)
            tv.setTextColor(textColor)
        }

        arrayOf(navDashboard, navSettings).forEach { tv ->
            tv.isFocusable = true
            tv.isClickable = true
            tv.setOnFocusChangeListener { v, hasFocus ->
                if (v is TextView) {
                    if (hasFocus) highlight(v) else unhighlight(v)
                }
            }
        }

        navDashboard.setOnClickListener {
            showSection(Section.DASHBOARD)
        }
        navSettings.setOnClickListener {
            showSection(Section.SETTINGS)
        }

        // Focus traversal from sidebar into content container
        navDashboard.nextFocusRightId = contentContainer.id
        navSettings.nextFocusRightId = contentContainer.id
    }

    private fun showSection(section: Section) {
        if (currentSection == section) return
        currentSection = section
        val fragment = when (section) {
            Section.DASHBOARD -> DashboardFragment.newInstance()
            Section.SETTINGS -> SettingsFragment.newInstance()
        }
        supportFragmentManager.beginTransaction()
            .replace(contentContainer.id, fragment, section.name)
            .commit()
        Toast.makeText(
            this,
            if (section == Section.DASHBOARD) getString(R.string.nav_dashboard) else getString(R.string.nav_settings),
            Toast.LENGTH_SHORT
        ).show()
    }
}
