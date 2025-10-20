package com.smarthome.tv

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.materialswitch.MaterialSwitch

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
        private val deviceSwitch: MaterialSwitch = itemView.findViewById(R.id.device_switch)

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
            
            // Set switch state without triggering listener
            deviceSwitch.setOnCheckedChangeListener(null)
            deviceSwitch.isChecked = device.isOn
            
            // Update card appearance based on state
            updateCardState(device.isOn)
            
            // Handle switch state changes
            deviceSwitch.setOnCheckedChangeListener { _, isChecked ->
                device.isOn = isChecked
                updateCardState(isChecked)
                onDeviceToggle(device)
            }
            
            // Handle focus changes on card
            deviceCard.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    deviceCard.animate().scaleX(1.05f).scaleY(1.05f).setDuration(120).start()
                    // Use translationZ to ensure shadow/elevation is respected across API levels
                    deviceCard.translationZ = 12f
                    deviceCard.elevation = 12f
                } else {
                    deviceCard.animate().scaleX(1.0f).scaleY(1.0f).setDuration(120).start()
                    deviceCard.translationZ = 0f
                    deviceCard.elevation = 0f
                }
            }
            
            // Handle DPAD center press on card to toggle switch
            deviceCard.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_CENTER,
                        KeyEvent.KEYCODE_ENTER,
                        KeyEvent.KEYCODE_SPACE -> {
                            deviceSwitch.toggle()
                            true
                        }
                        else -> false
                    }
                } else {
                    false
                }
            }
            
            // Handle click on card for touch/mouse input
            deviceCard.setOnClickListener {
                deviceSwitch.toggle()
            }
        }
        
        // Updates visual state of card based on device power state
        private fun updateCardState(isOn: Boolean) {
            val context = itemView.context
            
            if (isOn) {
                // ON state: card background color #565E71
                deviceCard.setCardBackgroundColor(
                    ContextCompat.getColor(context, R.color.card_on_bg)
                )
            } else {
                // OFF state: card background color #1B2027
                deviceCard.setCardBackgroundColor(
                    ContextCompat.getColor(context, R.color.card_off_bg)
                )
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
