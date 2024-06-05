package com.example.numberoperationsapp

import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var editText3: EditText
    private lateinit var buttonCalculate: Button
    private lateinit var buttonClear: Button
    private lateinit var textViewResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        val numberCommaFilter = InputFilter { source, _, _, _, _, _ ->
            val regex = Regex("[0-9,]*")
            if (source.matches(regex)) source else ""
        }

        editText1.filters = arrayOf(numberCommaFilter)
        editText2.filters = arrayOf(numberCommaFilter)
        editText3.filters = arrayOf(numberCommaFilter)

        buttonCalculate.setOnClickListener {
            val list1 = parseInput(editText1.text.toString())
            val list2 = parseInput(editText2.text.toString())
            val list3 = parseInput(editText3.text.toString())

            val intersection = list1.intersect(list2).intersect(list3)
            val union = list1.union(list2).union(list3)
            val highestNumber = union.maxOrNull()

            val resultText = getString(R.string.screenIntersectionText) + intersection.joinToString(", ") + "\n" +
                    getString(R.string.screenUnionText) + union.joinToString(", ") + "\n" +
                    getString(R.string.screenHighestNumberText) + highestNumber

            textViewResult.text = resultText
        }

        buttonClear.setOnClickListener {
            editText1.text.clear()
            editText2.text.clear()
            editText3.text.clear()
            editText1.requestFocus()
            textViewResult.text = ""
        }
    }

    private fun parseInput(input: String): List<Int> {
        return input.split(",")
            .mapNotNull { it.trim().toIntOrNull() }
            .filter { it.toString().isNotEmpty() }
    }

    private fun initView() {
        editText1 = findViewById(R.id.editText1)
        editText2 = findViewById(R.id.editText2)
        editText3 = findViewById(R.id.editText3)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        buttonClear = findViewById(R.id.buttonClear)
        textViewResult = findViewById(R.id.textViewResult)
    }
}
