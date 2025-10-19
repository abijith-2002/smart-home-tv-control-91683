package com.smarthome.tv.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smarthome.tv.R

/**
 * PUBLIC_INTERFACE
 * ActivityFragment
 * A simple placeholder fragment that displays an "Activity" heading.
 * This is navigable via TV DPAD and is shown when the Activity icon is selected.
 */
class ActivityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    companion object {
        // PUBLIC_INTERFACE
        fun newInstance(): ActivityFragment {
            /** Factory for ActivityFragment. */
            return ActivityFragment()
        }
    }
}
