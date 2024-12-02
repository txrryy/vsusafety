package com.example.vsupatrols

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RecentAlertsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recent_alerts)

        // Set the title of the activity
        title = "Recent Alerts"

        // Initialize the back button
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Set a listener for the back button
        btnBack.setOnClickListener {
            // When the back button is pressed, finish the activity and go back
            finish()
        }
    }
}