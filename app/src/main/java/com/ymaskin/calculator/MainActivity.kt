package com.ymaskin.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import com.ymaskin.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpLogic()
    }

    @SuppressLint("SetTextI18n")
    private fun setUpLogic() {
        with(binding) {
            setButtonsListener(btnZero)
            setButtonsListener(btnOne)
            setButtonsListener(btnTwo)
            setButtonsListener(btnThree)
            setButtonsListener(btnFour)
            setButtonsListener(btnFive)
            setButtonsListener(btnSix)
            setButtonsListener(btnSeven)
            setButtonsListener(btnEight)
            setButtonsListener(btnNine)
            setButtonsListener(btnPoint)
            setButtonsListener(btnAdd)
            setButtonsListener(btnDeduct)
            setButtonsListener(btnMultiply)
            setButtonsListener(btnDivide)

            btnEquals.setOnClickListener {
                with(result) {
                    if (text.isNullOrBlank() || text.isDigitsOnly()) {
                        Toast.makeText(
                            this@MainActivity,
                            "Invalid operation",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        try {
                            when {
                                text.contains("+") -> {
                                    val splitText = text.split("+")
                                    val firstNumber = splitText.first().toFloat()
                                    val secondNumber = splitText.last().toFloat()
                                    result.text = "${firstNumber + secondNumber}"
                                        .removeTrailingZero()
                                }

                                text.contains("-") -> {
                                    val splitText = text.split("-")
                                    val firstNumber = splitText.first().toFloat()
                                    val secondNumber = splitText.last().toFloat()
                                    result.text = "${firstNumber - secondNumber}"
                                        .removeTrailingZero()
                                }

                                text.contains("x") -> {
                                    val splitText = text.split("x")
                                    val firstNumber = splitText.first().toFloat()
                                    val secondNumber = splitText.last().toFloat()
                                    result.text = "${firstNumber * secondNumber}"
                                        .removeTrailingZero()
                                }

                                text.contains("%") -> {
                                    val splitText = text.split("%")
                                    val firstNumber = splitText.first().toFloat()
                                    val secondNumber = splitText.last().toFloat()
                                    if (secondNumber == 0f) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Cannot divide by 0",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    } else {
                                        result.text = "${firstNumber / secondNumber}"
                                            .removeTrailingZero()
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@MainActivity,
                                "Operation not supported",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            btnDelete.setOnClickListener {
                result.text = ""
            }
        }
    }

    private fun setButtonsListener(btn: Button) {
        btn.apply {
            setOnClickListener {
                if (text.toString().containsArithmeticOperator()) {
                    if (binding.result.text.toString().containsArithmeticOperator()) {
                        Toast.makeText(
                            this@MainActivity,
                            "Only one operator is supported",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        binding.result.append(text)
                    }
                } else {
                    binding.result.append(text)
                }
            }
        }
    }

    private fun String.containsArithmeticOperator(): Boolean {
        val arithmeticOperation = listOf('+', '-', 'x', '%')
        return any { arithmeticOperation.contains(it) }
    }

    private fun String.removeTrailingZero(): String {
        return if (endsWith(".0")) replace(".0", "") else this
    }
}