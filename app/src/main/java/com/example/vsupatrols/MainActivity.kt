package com.example.vsupatrols

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    private var panicHandler: Handler? = null
    private var isPanicTriggered = false
    private var animator: ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme
        val isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        if (isDarkMode) {
            setTheme(R.style.Theme_App_Dark)
        } else {
            setTheme(R.style.Theme_App_Light)
        }
        setContentView(R.layout.activity_main)

        // Initialize light mode switch
        val switch = findViewById<Switch>(R.id.switch1)
        switch.isChecked = isDarkMode
        switch.text = if (isDarkMode) "Dark Mode" else "Light Mode"

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switch.text = "Dark Mode"
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switch.text = "Light Mode"
            }
            restartActivity()
        }

        // Initialize other buttons
        val btnSafetyAlerts = findViewById<Button>(R.id.btnSafetyAlerts)

        val btnShareLocation = findViewById<Button>(R.id.btnShareLocation)
        val btnPanic = findViewById<Button>(R.id.btnPanic)
        val btnEmergencyContacts = findViewById<ImageButton>(R.id.btnEmergencyContacts)

        // Set an onClickListener for the button
        btnEmergencyContacts.setOnClickListener {
            // Open the Emergency Contacts Activity
            val intent = Intent(this, EmergencyContactsActivity::class.java)
            startActivity(intent)
        }
        // Set click listeners for safety alerts and share location
        btnSafetyAlerts.setOnClickListener {
            // Open the Recent Alerts activity when the button is clicked
                val intent = Intent(this, RecentAlertsActivity::class.java)
                startActivity(intent)


            Toast.makeText(this, "Opening Safety Alerts...", Toast.LENGTH_SHORT).show()
        }

        btnShareLocation.setOnClickListener {
            shareCurrentLocation()
        }

        // Panic Button with Hold Feature
        btnPanic.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startPanicHold(btnPanic)
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    cancelPanicHold(btnPanic)
                    true
                }
                else -> false
            }
        }
    }

    private fun startPanicHold(button: Button) {
        panicHandler = Handler(Looper.getMainLooper())
        isPanicTriggered = false

        // Start button animation
        animator = ObjectAnimator.ofFloat(button, "alpha", 1f, 0.5f).apply {
            duration = 3000
            start()
        }

        panicHandler?.postDelayed({
            if (!isPanicTriggered) {
                isPanicTriggered = true
                triggerPanicAlert()
                animator?.cancel()
                button.alpha = 1f
            }
        }, 3000)
    }

    private fun cancelPanicHold(button: Button) {
        panicHandler?.removeCallbacksAndMessages(null)
        animator?.cancel() // Cancel the animation
        button.alpha = 1f // Reset button appearance
    }

    private fun triggerPanicAlert() {
        Toast.makeText(this, "Panic Button Activated!", Toast.LENGTH_SHORT).show()
        // Logic for panic alert (e.g., notify emergency services)
    }

    private fun shareCurrentLocation() {
        val locationMessage = "This is my location: https://maps.google.com/?q=37.7749,-122.4194"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, locationMessage)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, "Share location via"))
    }

    private fun restartActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
