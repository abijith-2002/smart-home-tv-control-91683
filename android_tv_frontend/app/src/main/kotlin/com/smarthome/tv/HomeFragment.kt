package com.smarthome.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

// PUBLIC_INTERFACE
/**
 * Fragment displaying the home screen with smart home devices in a grid
 */
class HomeFragment : Fragment() {

    private lateinit var devicesRecyclerView: RecyclerView
    private lateinit var deviceCountText: TextView
    private val devices = mutableListOf<Device>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        devicesRecyclerView = view.findViewById(R.id.devices_recycler_view)
        deviceCountText = view.findViewById(R.id.device_count)

        // Add Device button placeholder click handler
        view.findViewById<View?>(R.id.btn_add_device)?.setOnClickListener {
            // Placeholder: In future, open "Add Device" flow or dialog
            // For now, you can log or show a transient UI if desired.
        }
        
        setupDevices()
        setupRecyclerView()
    }

    // Initialize sample devices matching the design
    private fun setupDevices() {
        devices.clear()
        devices.addAll(
            listOf(
                Device(1, "Living Room Light", DeviceType.LIGHT, true),
                Device(2, "Living Room Fan", DeviceType.FAN, false),
                Device(3, "Dining Room Fan", DeviceType.FAN, false),
                Device(4, "Bedroom 1 Light", DeviceType.LIGHT, false),
                Device(5, "Bedroom 1 AC", DeviceType.AC, true),
                Device(6, "Kitchen Light", DeviceType.LIGHT, false)
            )
        )
        
        deviceCountText.text = devices.size.toString()
    }

    // Setup RecyclerView with grid layout for device cards
    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 3)
        devicesRecyclerView.layoutManager = gridLayoutManager
        
        // Add custom spacing decoration with optimized gaps
        val spacingPx = resources.getDimensionPixelSize(R.dimen.device_card_margin)
        val itemDecoration = GridSpacingItemDecoration(
            spanCount = 3,
            spacing = spacingPx, // Use full margin for consistent inter-item spacing
            includeEdge = false
        )
        devicesRecyclerView.addItemDecoration(itemDecoration)
        
        val adapter = DeviceAdapter(devices) { device ->
            // Handle device toggle
            updateDeviceState(device)
        }
        
        devicesRecyclerView.adapter = adapter
    }

    // Update device state when toggled
    private fun updateDeviceState(device: Device) {
        // In a real app, this would send a command to the device
        // For now, state is already updated in the adapter
    }
}
