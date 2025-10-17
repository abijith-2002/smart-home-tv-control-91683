package com.smarthome.tv.ui.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.smarthome.tv.R

/**
 * PUBLIC_INTERFACE
 * DashboardFragment
 * Displays focusable device cards: Smart Light, Fan, Air Conditioner.
 * Each card is D-pad navigable, shows a visible focus state, and toggles
 * local state with a Toast on click.
 */
class DashboardFragment : Fragment() {

    private var lightOn = false
    private var fanOn = false
    private var acOn = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fun setupCard(card: CardView, label: TextView, icon: ImageView, title: String, initialOn: Boolean, onToggle: (Boolean) -> Unit) {
            var isOn = initialOn
            card.isFocusable = true
            card.isClickable = true
            updateCardUI(card, label, icon, title, isOn, requireContext())

            card.setOnClickListener {
                isOn = !isOn
                updateCardUI(card, label, icon, title, isOn, requireContext())
                onToggle(isOn)
            }

            card.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    card.cardElevation = 12f
                } else {
                    card.cardElevation = 4f
                }
            }
        }

        val cardLight: CardView = view.findViewById(R.id.cardLight)
        val cardFan: CardView = view.findViewById(R.id.cardFan)
        val cardAC: CardView = view.findViewById(R.id.cardAC)

        setupCard(
            cardLight,
            view.findViewById(R.id.labelLight),
            view.findViewById(R.id.iconLight),
            getString(R.string.device_light),
            lightOn
        ) {
            lightOn = it
            Toast.makeText(requireContext(), "${getString(R.string.device_light)}: " + if (it) "On" else "Off", Toast.LENGTH_SHORT).show()
        }

        setupCard(
            cardFan,
            view.findViewById(R.id.labelFan),
            view.findViewById(R.id.iconFan),
            getString(R.string.device_fan),
            fanOn
        ) {
            fanOn = it
            Toast.makeText(requireContext(), "${getString(R.string.device_fan)}: " + if (it) "On" else "Off", Toast.LENGTH_SHORT).show()
        }

        setupCard(
            cardAC,
            view.findViewById(R.id.labelAC),
            view.findViewById(R.id.iconAC),
            getString(R.string.device_ac),
            acOn
        ) {
            acOn = it
            Toast.makeText(requireContext(), "${getString(R.string.device_ac)}: " + if (it) "On" else "Off", Toast.LENGTH_SHORT).show()
        }

        // Focus order left to sidebar via root container's nextFocusLeft is set from activity
    }

    private fun updateCardUI(
        card: CardView,
        label: TextView,
        icon: ImageView,
        title: String,
        isOn: Boolean,
        context: Context
    ) {
        label.text = "$title • ${if (isOn) "On" else "Off"}"
        val color = if (isOn) R.color.colorSecondary else R.color.colorOnSurface
        label.setTextColor(context.getColor(color))
        icon.imageTintList = android.content.res.ColorStateList.valueOf(context.getColor(color))
    }

    companion object {
        // PUBLIC_INTERFACE
        fun newInstance(): DashboardFragment {
            /** Factory for DashboardFragment. */
            return DashboardFragment()
        }
    }
}
