package com.smarthome.tv.ui.screens

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.smarthome.tv.R

/**
 * PUBLIC_INTERFACE
 * DeviceAdapter
 * Adapter for displaying a TV-friendly grid of device cards with focus highlight,
 * DPAD navigation, and DPAD_CENTER toggling of ON/OFF state.
 */
class DeviceAdapter(
    private val items: MutableList<Device>,
    private val onToggle: (position: Int, device: Device) -> Unit
) : RecyclerView.Adapter<DeviceAdapter.DeviceVH>() {

    /** Device type mapping determines icon ligature and contentDescription. */
    enum class DeviceType { LIGHT, FAN, AC }

    data class Device(
        val id: String,
        val name: String,
        val type: DeviceType,
        var isOn: Boolean
    )

    class DeviceVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val card: MaterialCardView = itemView.findViewById(R.id.cardRoot)
        val title: TextView = itemView.findViewById(R.id.deviceTitle)
        val state: TextView = itemView.findViewById(R.id.deviceState)
        val icon: TextView = itemView.findViewById(R.id.deviceIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_device_card, parent, false)
        return DeviceVH(view)
    }

    override fun onBindViewHolder(holder: DeviceVH, position: Int) {
        val ctx = holder.itemView.context
        val device = items[position]

        holder.title.text = device.name
        bindIcon(holder, device)
        applyState(ctx, holder, device.isOn)

        // Enhanced accessibility
        val stateText = if (device.isOn) ctx.getString(R.string.state_on) else ctx.getString(R.string.state_off)
        holder.card.contentDescription = "${device.name}, $stateText"
        ViewCompat.setStateDescription(holder.card, stateText)

        // Focus visuals for TV
        holder.card.setOnFocusChangeListener { v, hasFocus ->
            val card = v as MaterialCardView
            if (hasFocus) {
                card.strokeColor = ContextCompat.getColor(ctx, R.color.focus_border)
                card.strokeWidth = (ctx.resources.displayMetrics.density * 2).toInt()
                card.cardElevation = 10f
                card.scaleX = 1.03f
                card.scaleY = 1.03f
            } else {
                card.strokeWidth = 0
                card.cardElevation = 2f
                card.scaleX = 1.0f
                card.scaleY = 1.0f
            }
        }

        // Toggle on click
        holder.card.setOnClickListener {
            device.isOn = !device.isOn
            applyState(ctx, holder, device.isOn)
            // Update state description
            val updatedState = if (device.isOn) ctx.getString(R.string.state_on) else ctx.getString(R.string.state_off)
            holder.card.contentDescription = "${device.name}, $updatedState"
            ViewCompat.setStateDescription(holder.card, updatedState)
            onToggle(position, device)
        }

        // Toggle on DPAD_CENTER / ENTER / SPACE
        holder.card.setOnKeyListener { _, keyCode, event ->
            if (event.action != KeyEvent.ACTION_DOWN) return@setOnKeyListener false
            if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER ||
                keyCode == KeyEvent.KEYCODE_ENTER ||
                keyCode == KeyEvent.KEYCODE_SPACE
            ) {
                device.isOn = !device.isOn
                applyState(ctx, holder, device.isOn)
                val updatedState = if (device.isOn) ctx.getString(R.string.state_on) else ctx.getString(R.string.state_off)
                holder.card.contentDescription = "${device.name}, $updatedState"
                ViewCompat.setStateDescription(holder.card, updatedState)
                onToggle(position, device)
                return@setOnKeyListener true
            }
            false
        }
    }

    private fun bindIcon(holder: DeviceVH, device: Device) {
        // Use Material Icons ligatures for specific icons
        when (device.type) {
            DeviceType.LIGHT -> {
                holder.icon.text = "lightbulb"
                holder.icon.contentDescription = holder.itemView.context.getString(R.string.device_icon_light)
            }
            DeviceType.FAN -> {
                holder.icon.text = "mode_fan"
                holder.icon.contentDescription = holder.itemView.context.getString(R.string.device_icon_fan)
            }
            DeviceType.AC -> {
                holder.icon.text = "ac_unit"
                holder.icon.contentDescription = holder.itemView.context.getString(R.string.device_icon_ac)
            }
        }
    }

    private fun applyState(ctx: android.content.Context, holder: DeviceVH, isOn: Boolean) {
        val onColor = ContextCompat.getColor(ctx, R.color.device_on_bg)
        val offColor = ContextCompat.getColor(ctx, R.color.device_off_bg)
        holder.card.setCardBackgroundColor(if (isOn) onColor else offColor)

        holder.state.text = if (isOn) ctx.getString(R.string.state_on) else ctx.getString(R.string.state_off)
        holder.state.setTextColor(
            if (isOn) ContextCompat.getColor(ctx, R.color.colorBackground)
            else ContextCompat.getColor(ctx, R.color.colorOnBackground)
        )

        // Icon tint reflects ON/OFF
        holder.icon.setTextColor(
            if (isOn) ContextCompat.getColor(ctx, R.color.device_icon_on)
            else ContextCompat.getColor(ctx, R.color.device_icon_off)
        )
    }

    override fun getItemCount(): Int = items.size
}
