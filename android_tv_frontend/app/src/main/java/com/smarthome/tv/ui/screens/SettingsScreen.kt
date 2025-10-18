package com.smarthome.tv.ui.screens

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.smarthome.tv.R

/**
 * PUBLIC_INTERFACE
 * SettingsFragment
 * Shows placeholder items (Account, Network, About) that are fully navigable
 * via remote and respond to click events with Toast feedback.
 */
class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val items = listOf<TextView>(
            view.findViewById(R.id.itemAccount),
            view.findViewById(R.id.itemNetwork),
            view.findViewById(R.id.itemAbout)
        )

        val regular = ResourcesCompat.getFont(requireContext(), R.font.figtree_family_regular)
        val focusBorder = ContextCompat.getColor(requireContext(), R.color.focus_border)

        fun focusBg(): GradientDrawable = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = resources.displayMetrics.density * 12f
            setStroke((resources.displayMetrics.density * 2f).toInt(), focusBorder)
            setColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
        }

        items.forEach { tv ->
            tv.isFocusable = true
            tv.isClickable = true
            tv.typeface = regular
            tv.setOnClickListener {
                Toast.makeText(requireContext(), tv.text.toString(), Toast.LENGTH_SHORT).show()
            }
            tv.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.background = focusBg()
                    (v as TextView).setTextColor(requireContext().getColor(R.color.colorPrimary))
                    v.elevation = 6f
                } else {
                    v.background = null
                    (v as TextView).setTextColor(requireContext().getColor(R.color.colorOnBackground))
                    v.elevation = 0f
                }
            }
        }
    }

    companion object {
        // PUBLIC_INTERFACE
        fun newInstance(): SettingsFragment {
            /** Factory for SettingsFragment. */
            return SettingsFragment()
        }
    }
}
