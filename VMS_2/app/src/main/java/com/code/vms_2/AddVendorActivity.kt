package com.code.vms_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.code.vms_2.database.DBHelper

class AddVendorActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vendor)

        dbHelper = DBHelper(this)

        val edtName = findViewById<EditText>(R.id.edtVendorName)
        val edtStall = findViewById<EditText>(R.id.edtStallNo)
        val edtType = findViewById<EditText>(R.id.edtBusinessType)
        val edtPhone = findViewById<EditText>(R.id.edtPhone)
        val btnSave = findViewById<Button>(R.id.btnSaveVendor)

        btnSave.setOnClickListener {
            val name = edtName.text.toString()
            val stall = edtStall.text.toString()
            val type = edtType.text.toString()
            val phone = edtPhone.text.toString()

            if (name.isNotEmpty() && stall.isNotEmpty() && type.isNotEmpty() && phone.isNotEmpty()) {
                val inserted = dbHelper.insertVendor(name, stall, type, phone)
                if (inserted) {
                    Toast.makeText(this, "Vendor added!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to add vendor.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
