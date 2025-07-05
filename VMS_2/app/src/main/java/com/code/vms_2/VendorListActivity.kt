package com.code.vms_2

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.code.vms_2.database.DBHelper

class VendorListActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var listView: ListView
    private lateinit var vendorList: ArrayList<String>
    private lateinit var vendorIds: ArrayList<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vendor_list)

        dbHelper = DBHelper(this)
        listView = findViewById(R.id.vendorListView)

        loadVendors()

        listView.setOnItemClickListener { _, _, position, _ ->
            val id = vendorIds[position]
            val name = vendorList[position]

            val options = arrayOf("Edit", "Delete")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose action for $name")
            builder.setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        val intent = Intent(this, EditVendorActivity::class.java)
                        intent.putExtra("vendor_id", id)
                        startActivity(intent)
                    }
                    1 -> {
                        dbHelper.deleteVendor(id)
                        loadVendors()
                        Toast.makeText(this, "Vendor deleted", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            builder.show()
        }
    }

    private fun loadVendors() {
        vendorList = ArrayList()
        vendorIds = ArrayList()

        val cursor = dbHelper.getAllVendors()
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val name = cursor.getString(1)
                val stall = cursor.getString(2)
                val type = cursor.getString(3)
                val phone = cursor.getString(4)

                vendorList.add("$name\nStall: $stall | $type | $phone")
                vendorIds.add(id)

            } while (cursor.moveToNext())
        }
        cursor.close()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, vendorList)
        listView.adapter = adapter
    }
}
