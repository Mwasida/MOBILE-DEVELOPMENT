package com.code.vms_2


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddVendor = findViewById<Button>(R.id.btnAddVendor)
        val btnVendorList = findViewById<Button>(R.id.btnVendorList)
        val btnAddPayment = findViewById<Button>(R.id.btnAddPayment)
        val btnViewPayments = findViewById<Button>(R.id.btnViewPayments)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // optional but recommended
        }

        btnAddVendor.setOnClickListener {
            startActivity(Intent(this, AddVendorActivity::class.java))
        }

        btnVendorList.setOnClickListener {
            startActivity(Intent(this, VendorListActivity::class.java))
        }

        btnAddPayment.setOnClickListener {
            startActivity(Intent(this, AddPaymentActivity::class.java))
        }

        btnViewPayments.setOnClickListener {
            startActivity(Intent(this, PaymentHistoryActivity::class.java))
        }
    }
}
