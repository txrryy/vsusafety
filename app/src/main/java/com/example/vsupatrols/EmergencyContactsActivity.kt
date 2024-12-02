package com.example.vsupatrols


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar


class EmergencyContactsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emergency_contacts)
        // Enable the up (back) button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Sample emergency contacts (replace with real data)
        val contacts = arrayOf(
            "Campus Police: (123) 456-7890",
            "Health Center: (123) 555-6789",
            "Security Office: (123) 555-1234"
        )

        // Find the back button and set up the onClickListener
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            onBackPressed()  // Go back to the previous activity
        }

        // Set up a ListView to display the contacts
        val listView: ListView = findViewById(R.id.listViewEmergencyContacts)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, contacts)
        listView.adapter = adapter
    }




}
