package com.example.calculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var txtResult: TextView
    private var result = 0
    private var inStr = "0"
    private var lastOperator = ' '

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtResult = findViewById(R.id.txtResultId)
        txtResult.text = "0"

        val buttons = listOf(
            R.id.btnNum0Id, R.id.btnNum1Id, R.id.btnNum2Id, R.id.btnNum3Id, R.id.btnNum4Id,
            R.id.btnNum5Id, R.id.btnNum6Id, R.id.btnNum7Id, R.id.btnNum8Id, R.id.btnNum9Id,
            R.id.btnAddId, R.id.btnSubId, R.id.btnMulId, R.id.btnDivId,
            R.id.btnClearId, R.id.btnEqualId
        )

        val listener = ButtonClickListener()

        buttons.forEach {
            findViewById<Button>(it).setOnClickListener(listener)
        }
    }

    inner class ButtonClickListener : android.view.View.OnClickListener {
        override fun onClick(view: android.view.View) {
            val btn = view as Button
            when (view.id) {
                R.id.btnNum0Id, R.id.btnNum1Id, R.id.btnNum2Id, R.id.btnNum3Id,
                R.id.btnNum4Id, R.id.btnNum5Id, R.id.btnNum6Id,
                R.id.btnNum7Id, R.id.btnNum8Id, R.id.btnNum9Id -> {
                    val digit = btn.text.toString()
                    inStr = if (inStr == "0") digit else inStr + digit
                    txtResult.text = inStr
                    if (lastOperator == '=') {
                        result = 0
                        lastOperator = ' '
                    }
                }

                R.id.btnAddId -> {
                    compute()
                    lastOperator = '+'
                }

                R.id.btnSubId -> {
                    compute()
                    lastOperator = '-'
                }

                R.id.btnMulId -> {
                    compute()
                    lastOperator = '*'
                }

                R.id.btnDivId -> {
                    compute()
                    lastOperator = '/'
                }

                R.id.btnEqualId -> {
                    compute()
                    lastOperator = '='
                }

                R.id.btnClearId -> {
                    result = 0
                    inStr = "0"
                    lastOperator = ' '
                    txtResult.text = "0"
                }
            }
        }

        private fun compute() {
            val inNum = inStr.toInt()
            inStr = "0"
            result = when (lastOperator) {
                ' ' -> inNum
                '+' -> result + inNum
                '-' -> result - inNum
                '*' -> result * inNum
                '/' -> result / inNum
                '=' -> result
                else -> result
            }
            txtResult.text = result.toString()
        }
    }
}


