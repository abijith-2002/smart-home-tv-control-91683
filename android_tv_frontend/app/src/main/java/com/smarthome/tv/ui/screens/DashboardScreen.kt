package com.smarthome.tv.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smarthome.tv.R

/**
 * PUBLIC_INTERFACE
 * DashboardFragment
 * Native Android TV dashboard with:
 * - Persistent left sidebar (handled by MainActivity)
 * - "Devices" heading with dynamic ON device count bubble
 * - RecyclerView grid of focusable device cards
 * - DPAD_CENTER toggles card ON/OFF and updates visuals + count
 */
class DashboardFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var countView: TextView
    private lateinit var adapter: DeviceAdapter

    private val devices = mutableListOf(
        DeviceAdapter.Device(id = "dev1", name = "Living Room Light", type = DeviceAdapter.DeviceType.LIGHT, isOn = true),
        DeviceAdapter.Device(id = "dev2", name = "Living Room Fan", type = DeviceAdapter.DeviceType.FAN, isOn = false),
        DeviceAdapter.Device(id = "dev3", name = "Dining Room Fan", type = DeviceAdapter.DeviceType.FAN, isOn = false),
        DeviceAdapter.Device(id = "dev4", name = "Bedroom 1 Light", type = DeviceAdapter.DeviceType.LIGHT, isOn = false),
        DeviceAdapter.Device(id = "dev5", name = "Bedroom 1 AC", type = DeviceAdapter.DeviceType.AC, isOn = true),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        countView = view.findViewById(R.id.devicesCount)
        recyclerView = view.findViewById(R.id.devicesGrid)

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        adapter = DeviceAdapter(devices) { _, _ ->
            updateCount()
        }
        recyclerView.adapter = adapter

        updateCount()

        // Give initial focus to first card for TV DPAD
        recyclerView.post {
            val holder = recyclerView.findViewHolderForAdapterPosition(0)
            if (holder != null) {
                holder.itemView.requestFocus()
            } else if (recyclerView.childCount > 0) {
                recyclerView.getChildAt(0)?.requestFocus()
            }
        }
    }

    private fun updateCount() {
        // Count ON devices dynamically as per DPAD toggles
        val onCount = devices.count { it.isOn }
        countView.text = onCount.toString()
    }

    companion object {
        // PUBLIC_INTERFACE
        fun newInstance(): DashboardFragment {
            /** Factory for DashboardFragment. */
            return DashboardFragment()
        }
    }
}
