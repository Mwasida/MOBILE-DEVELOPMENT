package com.code.vms_2

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VendorDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor_dashboard)

        val username = intent.getStringExtra("username")
        val textView = findViewById<TextView>(R.id.txtVendorWelcome)
        textView.text = "Welcome, $username"
    }
}
