package com.example.numberoperationsapp

import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText1 = findViewById<EditText>(R.id.editText1)
        val editText2 = findViewById<EditText>(R.id.editText2)
        val editText3 = findViewById<EditText>(R.id.editText3)
        val buttonCalculate = findViewById<Button>(R.id.buttonCalculate)
        val buttonClear = findViewById<TextView>(R.id.buttonClear)
        val textViewResult = findViewById<TextView>(R.id.textViewResult)

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
}
