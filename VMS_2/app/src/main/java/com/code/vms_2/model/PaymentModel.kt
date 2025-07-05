package com.code.vms_2.model

data class PaymentModel(
    val id: Int,
    val vendorName: String,
    val amount: Double,
    val date: String
)
