package com.code.vms_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.code.vms_2.database.DBHelper

class EditVendorActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    private var vendorId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_vendor)

        dbHelper = DBHelper(this)

        val edtName = findViewById<EditText>(R.id.edtVendorName)
        val edtStall = findViewById<EditText>(R.id.edtStallNo)
        val edtType = findViewById<EditText>(R.id.edtBusinessType)
        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        val btnUpdate = findViewById<Button>(R.id.btnUpdateVendor)

        // Get vendor ID from intent
        vendorId = intent.getIntExtra("vendor_id", -1)
        if (vendorId == -1) {
            Toast.makeText(this, "Invalid Vendor", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Load vendor info
        val vendor = dbHelper.getVendorById(vendorId)
        if (vendor != null) {
            edtName.setText(vendor.name)
            edtStall.setText(vendor.stallNo)
            edtType.setText(vendor.businessType)
            edtPhone.setText(vendor.phone)
        }

        btnUpdate.setOnClickListener {
            val name = edtName.text.toString()
            val stall = edtStall.text.toString()
            val type = edtType.text.toString()
            val phone = edtPhone.text.toString()

            val updated = dbHelper.updateVendor(vendorId, name, stall, type, phone)
            if (updated) {
                Toast.makeText(this, "Vendor updated", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
