package com.smarthome.tv

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

// PUBLIC_INTERFACE
/**
 * RecyclerView adapter for displaying smart home device cards with TV remote support
 * @property devices List of Device objects to display
 * @property onDeviceToggle Callback when device state is toggled
 */
class DeviceAdapter(
    private val devices: List<Device>,
    private val onDeviceToggle: (Device) -> Unit
) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    // PUBLIC_INTERFACE
    /**
     * ViewHolder for device card items with focus and toggle handling
     */
    inner class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val deviceCard: CardView = itemView.findViewById(R.id.device_card)
        private val deviceName: TextView = itemView.findViewById(R.id.device_name)
        private val deviceIcon: ImageView = itemView.findViewById(R.id.device_icon)
        private val switchBg: View = itemView.findViewById(R.id.switch_bg)
        private val switchText: TextView = itemView.findViewById(R.id.switch_text)
        private val switchContainer: FrameLayout = itemView.findViewById(R.id.switch_container)

        // PUBLIC_INTERFACE
        /**
         * Binds device data to the view holder and sets up interaction handlers
         * @param device The Device object to display
         */
        fun bind(device: Device) {
            deviceName.text = device.name
            
            // Set device icon based on type
            when (device.type) {
                DeviceType.LIGHT -> deviceIcon.setImageResource(R.drawable.lightbulb_24)
                DeviceType.FAN -> deviceIcon.setImageResource(R.drawable.mode_fan_24)
                DeviceType.AC -> deviceIcon.setImageResource(R.drawable.ac_unit_24)
            }
            
            // Update card appearance based on state
            updateCardState(device.isOn)
            
            // Handle focus changes
            deviceCard.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    deviceCard.scaleX = 1.05f
                    deviceCard.scaleY = 1.05f
                    deviceCard.elevation = 12f
                } else {
                    deviceCard.scaleX = 1.0f
                    deviceCard.scaleY = 1.0f
                    deviceCard.elevation = 0f
                }
            }
            
            // Handle DPAD center press and Enter/Space for toggle
            deviceCard.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_CENTER,
                        KeyEvent.KEYCODE_ENTER,
                        KeyEvent.KEYCODE_SPACE -> {
                            device.isOn = !device.isOn
                            updateCardState(device.isOn)
                            onDeviceToggle(device)
                            true
                        }
                        else -> false
                    }
                } else {
                    false
                }
            }
            
            // Also handle click for touch/mouse input
            deviceCard.setOnClickListener {
                device.isOn = !device.isOn
                updateCardState(device.isOn)
                onDeviceToggle(device)
            }
        }
        
        // Updates visual state of card based on device power state
        private fun updateCardState(isOn: Boolean) {
            val context = itemView.context
            
            if (isOn) {
                // ON state: light blue background
                deviceCard.setCardBackgroundColor(
                    ContextCompat.getColor(context, R.color.card_on_bg)
                )
                switchBg.background = ContextCompat.getDrawable(context, R.drawable.switch_on_bg)
                switchText.text = context.getString(R.string.device_on)
            } else {
                // OFF state: darker background
                deviceCard.setCardBackgroundColor(
                    ContextCompat.getColor(context, R.color.card_off_bg)
                )
                switchBg.background = ContextCompat.getDrawable(context, R.drawable.switch_off_bg)
                switchText.text = context.getString(R.string.device_off)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_device_card, parent, false)
        return DeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        holder.bind(devices[position])
    }

    override fun getItemCount(): Int = devices.size
}
