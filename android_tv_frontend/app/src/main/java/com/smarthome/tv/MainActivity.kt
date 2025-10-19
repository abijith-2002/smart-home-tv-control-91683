package com.smarthome.tv

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.smarthome.tv.R
import com.smarthome.tv.ui.screens.DashboardFragment
import com.smarthome.tv.ui.screens.SettingsFragment
import com.smarthome.tv.ui.screens.ActivityFragment

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

    private enum class Section { DASHBOARD, ACTIVITY, SETTINGS }

    private var currentSection: Section = Section.DASHBOARD

    private lateinit var navHomeIcon: ImageView
    private lateinit var navActivityIcon: ImageView
    private lateinit var navSettingsIcon: ImageView
    private lateinit var contentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Force reference to R to ensure correct package binding
        val layoutId = R.layout.activity_main
        setContentView(layoutId)

        navHomeIcon = findViewById(R.id.navHomeIcon)
        navActivityIcon = findViewById(R.id.navActivityIcon)
        navSettingsIcon = findViewById(R.id.navSettingsIcon)
        contentContainer = findViewById(R.id.contentContainer)

        setupSidebar()

        // Only load the initial section once
        if (savedInstanceState == null) {
            showSection(Section.DASHBOARD)
        }

        // Start focus on sidebar Home icon for TV
        navHomeIcon.isFocusable = true
        navHomeIcon.requestFocus()
    }

    private fun roundedFocusBackground(strokeColor: Int): GradientDrawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = resources.displayMetrics.density * 12f
            setStroke((resources.displayMetrics.density * 2f).toInt(), strokeColor)
            setColor(ContextCompat.getColor(this@MainActivity, android.R.color.transparent))
        }
    }

    private fun setupSidebar() {
        val focusBorder = ContextCompat.getColor(this, R.color.focus_border)

        val highlight: (ImageView) -> Unit = { v ->
            v.background = roundedFocusBackground(focusBorder)
            v.elevation = 6f
            v.alpha = 1.0f
            v.scaleX = 1.05f
            v.scaleY = 1.05f
        }
        val unhighlight: (ImageView) -> Unit = { v ->
            v.background = null
            v.elevation = 0f
            v.alpha = 0.95f
            v.scaleX = 1.0f
            v.scaleY = 1.0f
        }

        arrayOf(navHomeIcon, navActivityIcon, navSettingsIcon).forEach { iv ->
            iv.isFocusable = true
            iv.isClickable = true
            iv.setOnFocusChangeListener { v, hasFocus ->
                if (v is ImageView) {
                    if (hasFocus) highlight(v) else unhighlight(v)
                }
            }
        }

        navHomeIcon.setOnClickListener { showSection(Section.DASHBOARD) }
        navActivityIcon.setOnClickListener { showSection(Section.ACTIVITY) }
        navSettingsIcon.setOnClickListener { showSection(Section.SETTINGS) }

        // Focus traversal from sidebar into content container
        navHomeIcon.nextFocusRightId = contentContainer.id
        navActivityIcon.nextFocusRightId = contentContainer.id
        navSettingsIcon.nextFocusRightId = contentContainer.id
    }

    private fun showSection(section: Section) {
        currentSection = section
        val fragment = when (section) {
            Section.DASHBOARD -> DashboardFragment.newInstance()
            Section.ACTIVITY -> ActivityFragment.newInstance()
            Section.SETTINGS -> SettingsFragment.newInstance()
        }
        supportFragmentManager.beginTransaction()
            .replace(contentContainer.id, fragment, section.name)
            .commit()
        val label = when (section) {
            Section.DASHBOARD -> getString(R.string.nav_dashboard)
            Section.ACTIVITY -> getString(R.string.nav_activity)
            Section.SETTINGS -> getString(R.string.nav_settings)
        }
        Toast.makeText(this, label, Toast.LENGTH_SHORT).show()
    }
}
