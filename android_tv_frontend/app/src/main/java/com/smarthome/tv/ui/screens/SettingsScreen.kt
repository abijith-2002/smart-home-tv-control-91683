package com.smarthome.tv.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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

        items.forEach { tv ->
            tv.isFocusable = true
            tv.isClickable = true
            tv.setOnClickListener {
                Toast.makeText(requireContext(), tv.text.toString(), Toast.LENGTH_SHORT).show()
            }
            tv.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    v.setBackgroundColor(requireContext().getColor(R.color.focus_glow))
                    (v as TextView).setTextColor(requireContext().getColor(R.color.colorPrimary))
                } else {
                    v.setBackgroundColor(0x00000000)
                    (v as TextView).setTextColor(requireContext().getColor(R.color.colorOnBackground))
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
