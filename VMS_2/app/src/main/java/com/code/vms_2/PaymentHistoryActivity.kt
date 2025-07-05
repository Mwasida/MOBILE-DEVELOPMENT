package com.code.vms_2

import android.os.Bundle
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity
import com.code.vms_2.database.DBHelper
import com.code.vms_2.model.PaymentModel

class PaymentHistoryActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_history)

        dbHelper = DBHelper(this)
        listView = findViewById(R.id.paymentListView)

        val data = dbHelper.getAllPayments()
        val listItems = ArrayList<Map<String, String>>()

        for (payment in data) {
            val item = HashMap<String, String>()
            item["line1"] = "Vendor: ${payment.vendorName}"
            item["line2"] = "Amount: ${payment.amount} TZS"
            item["line3"] = "Date: ${payment.date}"
            listItems.add(item)
        }

        val adapter = SimpleAdapter(
            this, listItems, android.R.layout.simple_list_item_2,
            arrayOf("line1", "line2"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        listView.adapter = adapter
    }
}
