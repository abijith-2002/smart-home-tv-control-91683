package com.smarthome.tv.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.smarthome.tv.R

/**
 * PUBLIC_INTERFACE
 * DashboardFragment
 * Hosts a full-screen WebView that loads the SmartHome_Home Figma-extracted
 * HTML/CSS/JS screen from app assets. The page implements its own sidebar,
 * device grid, TV D-Pad focus navigation, and DPAD_CENTER toggling.
 *
 * Entry behavior:
 * - Loads file:///android_asset/smarthomehome-15-435.html
 * - Enables JavaScript and file access for local assets
 * - Requests focus so TV remotes send key events to the page
 */
class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val webView: WebView = view.findViewById(R.id.webView)

        // Configure WebView for local asset rendering and TV navigation
        val settings: WebSettings = webView.settings
        settings.javaScriptEnabled = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.allowContentAccess = true
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.builtInZoomControls = false
        settings.displayZoomControls = false
        settings.mediaPlaybackRequiresUserGesture = false

        webView.isFocusable = true
        webView.isFocusableInTouchMode = true
        webView.requestFocus()

        webView.webViewClient = object : WebViewClient() {}
        webView.webChromeClient = WebChromeClient()

        // Let the page handle DPAD keys; do not consume here
        webView.setOnKeyListener { _, keyCode, event ->
            // Ensure DPAD keys reach the WebView/page; return false to let it handle
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_LEFT,
                KeyEvent.KEYCODE_DPAD_RIGHT,
                KeyEvent.KEYCODE_DPAD_UP,
                KeyEvent.KEYCODE_DPAD_DOWN,
                KeyEvent.KEYCODE_DPAD_CENTER,
                KeyEvent.KEYCODE_ENTER,
                KeyEvent.KEYCODE_SPACE -> {
                    // Pass through
                    false
                }
                else -> false
            }
        }

        // Load the SmartHome_Home screen from assets
        webView.loadUrl("file:///android_asset/smarthomehome-15-435.html")
    }

    companion object {
        // PUBLIC_INTERFACE
        fun newInstance(): DashboardFragment {
            /** Factory for DashboardFragment. */
            return DashboardFragment()
        }
    }
}
