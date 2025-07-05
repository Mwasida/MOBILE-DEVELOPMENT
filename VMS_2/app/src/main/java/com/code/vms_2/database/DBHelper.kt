package com.code.vms_2.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.code.vms_2.model.PaymentModel
import com.code.vms_2.model.VendorModel

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "vms.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        // Create Users Table
        db.execSQL(
            "CREATE TABLE users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "username TEXT UNIQUE," +
                    "password TEXT," +
                    "role TEXT)"
        )

        // Create Vendors Table
        db.execSQL(
            "CREATE TABLE vendors (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "stall_no TEXT," +
                    "business_type TEXT," +
                    "phone TEXT)"
        )

        // Create Payments Table
        db.execSQL(
            "CREATE TABLE payments (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "vendor_id INTEGER," +
                    "amount REAL," +
                    "date TEXT," +
                    "FOREIGN KEY(vendor_id) REFERENCES vendors(id))"
        )


        // Insert default admin
        val cv = ContentValues()
        cv.put("username", "admin")
        cv.put("password", "admin123")
        cv.put("role", "ADMIN")
        db.insert("users", null, cv)


    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        db.execSQL("DROP TABLE IF EXISTS vendors")
        db.execSQL("DROP TABLE IF EXISTS payments")
        onCreate(db)
    }

    fun login(username: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE username = ? AND password = ?",
            arrayOf(username, password)
        )
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }
    fun insertVendor(name: String, stallNo: String, type: String, phone: String): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("stall_no", stallNo)
        cv.put("business_type", type)
        cv.put("phone", phone)
        val result = db.insert("vendors", null, cv)
        return result != -1L
    }
    fun getAllVendors(): android.database.Cursor {
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM vendors", null)
    }
    fun deleteVendor(id: Int): Boolean {
        val db = writableDatabase
        val result = db.delete("vendors", "id=?", arrayOf(id.toString()))
        return result > 0
    }
    fun insertPayment(vendorId: Int, amount: Double, date: String): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("vendor_id", vendorId)
        cv.put("amount", amount)
        cv.put("date", date)
        val result = db.insert("payments", null, cv)
        return result != -1L
    }
    fun getAllPayments(): List<PaymentModel> {
        val db = readableDatabase
        val query = """
        SELECT p.id, v.name, p.amount, p.date
        FROM payments p
        JOIN vendors v ON p.vendor_id = v.id
        ORDER BY p.date DESC
    """.trimIndent()

        val cursor = db.rawQuery(query, null)
        val list = mutableListOf<PaymentModel>()

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val vendorName = cursor.getString(1)
                val amount = cursor.getDouble(2)
                val date = cursor.getString(3)

                list.add(PaymentModel(id, vendorName, amount, date))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return list
    }
    fun getVendorById(id: Int): VendorModel? {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM vendors WHERE id = ?", arrayOf(id.toString()))
        var vendor: VendorModel? = null

        if (cursor.moveToFirst()) {
            val name = cursor.getString(1)
            val stall = cursor.getString(2)
            val type = cursor.getString(3)
            val phone = cursor.getString(4)
            vendor = VendorModel(id, name, stall, type, phone)
        }

        cursor.close()
        return vendor
    }

    fun updateVendor(id: Int, name: String, stallNo: String, type: String, phone: String): Boolean {
        val db = writableDatabase
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("stall_no", stallNo)
        cv.put("business_type", type)
        cv.put("phone", phone)
        val result = db.update("vendors", cv, "id=?", arrayOf(id.toString()))
        return result > 0
    }
    fun authenticateUser(username: String, password: String): String? {
        val db = readableDatabase
        val query = "SELECT role FROM users WHERE username = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))

        var role: String? = null
        if (cursor.moveToFirst()) {
            role = cursor.getString(0) // "ADMIN" or "VENDOR"
        }

        cursor.close()
        return role
    }




}
