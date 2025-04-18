package com.example.calculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var inputText: TextView
    private var currentInput: String = ""
    private var lastOperator: String? = null
    private var firstNumber: Double? = null
    private var resetOnNextInput = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputText = findViewById(R.id.tvInput)
        val buttonMap = mapOf(
            R.id.btn0 to "0",
            R.id.btn1 to "1",
            R.id.btn2 to "2",
            R.id.btn3 to "3",
            R.id.btn4 to "4",
            R.id.btn5 to "5",
            R.id.btn6 to "6",
            R.id.btn7 to "7",
            R.id.btn8 to "8",
            R.id.btn9 to "9",
            R.id.btnDot to "."
        )

        for ((id, value) in buttonMap) {
            findViewById<Button>(id).setOnClickListener {
                if (resetOnNextInput) {
                    currentInput = ""
                    resetOnNextInput = false
                }
                currentInput += value
                inputText.text = currentInput
            }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            currentInput = ""
            firstNumber = null
            lastOperator = null
            inputText.text = "0"
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorClicked("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { onOperatorClicked("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperatorClicked("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperatorClicked("/") }

        findViewById<Button>(R.id.btnEquals).setOnClickListener {
            if (firstNumber != null && lastOperator != null && currentInput.isNotEmpty()) {
                val secondNumber = currentInput.toDoubleOrNull()
                val result = when (lastOperator) {
                    "+" -> firstNumber!! + secondNumber!!
                    "-" -> firstNumber!! - secondNumber!!
                    "*" -> firstNumber!! * secondNumber!!
                    "/" -> if (secondNumber != 0.0) firstNumber!! / secondNumber!! else "Error"
                    else -> "Error"
                }
                inputText.text = result.toString()
                currentInput = result.toString()
                firstNumber = null
                lastOperator = null
                resetOnNextInput = true
            }
        }
    }

    private fun onOperatorClicked(operator: String) {
        if (currentInput.isNotEmpty()) {
            firstNumber = currentInput.toDoubleOrNull()
            lastOperator = operator
            currentInput = ""
            resetOnNextInput = false
        }
    }
}
