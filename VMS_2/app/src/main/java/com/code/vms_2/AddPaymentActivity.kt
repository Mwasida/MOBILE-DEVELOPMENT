package com.code.vms_2


import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.code.vms_2.database.DBHelper
import java.text.SimpleDateFormat
import java.util.*

class AddPaymentActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var vendorSpinner: Spinner
    private lateinit var edtAmount: EditText
    private lateinit var btnSavePayment: Button

    private val vendorMap = mutableMapOf<String, Int>() // name -> id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_payment)

        dbHelper = DBHelper(this)

        vendorSpinner = findViewById(R.id.spinnerVendors)
        edtAmount = findViewById(R.id.edtAmount)
        btnSavePayment = findViewById(R.id.btnSavePayment)

        loadVendors()

        btnSavePayment.setOnClickListener {
            val selectedVendorName = vendorSpinner.selectedItem.toString()
            val vendorId = vendorMap[selectedVendorName]
            val amount = edtAmount.text.toString().toDoubleOrNull()
            val date = getCurrentDate()

            if (vendorId != null && amount != null) {
                val success = dbHelper.insertPayment(vendorId, amount, date)
                if (success) {
                    Toast.makeText(this, "Payment saved", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadVendors() {
        val cursor = dbHelper.getAllVendors()
        val vendorNames = mutableListOf<String>()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                vendorMap[name] = id
                vendorNames.add(name)
            } while (cursor.moveToNext())
        }
        cursor.close()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, vendorNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        vendorSpinner.adapter = adapter
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }
}
