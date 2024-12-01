package com.example.lab1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab1.ui.theme.Lab1Theme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab1Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    SecondTaskApp(
                        modifier = Modifier.padding(innerPadding),
                        onBackPressed = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun SecondTaskApp(modifier: Modifier = Modifier, onBackPressed: () -> Unit) {
    // State for user input
    var inputValues by remember { mutableStateOf(mapOf<String, String>()) }
    var result by remember { mutableStateOf("") }

    val fields = listOf(
        "Вуглець (Cg, %)" to "carbon",
        "Водень (Hg, %)" to "hydrogen",
        "Кисень (Og, %)" to "oxygen",
        "Сірка (Sg, %)" to "sulfur",
        "Нижня теплота згоряння (Qdaf, МДж/кг)" to "qdaf",
        "Вологість робочої маси (Wp, %)" to "moisture",
        "Зольність сухої маси (Ap, %)" to "ash",
        "Ванадій (Vg, мг/кг)" to "vanadium"
    )

    // Helper function to parse input to Double or return 0.0 if invalid
    fun parseInput(input: String?): Double {
        return input?.toDoubleOrNull() ?: 0.0
    }

    // Function to calculate the result
    fun calculateSecondTask() {
        val C_G = parseInput(inputValues["carbon"])
        val H_G = parseInput(inputValues["hydrogen"])
        val O_G = parseInput(inputValues["oxygen"])
        val S_G = parseInput(inputValues["sulfur"])
        val Q_daf = parseInput(inputValues["qdaf"])
        val W_P = parseInput(inputValues["moisture"])
        val A_P = parseInput(inputValues["ash"])
        val V_G = parseInput(inputValues["vanadium"])
        val factor = (100 - W_P - A_P) / 100

        // Робоча маса
        val workMass = mapOf(
            "C" to C_G * factor,
            "H" to H_G * factor,
            "O" to O_G * factor,
            "S" to S_G * factor,
            "Q" to Q_daf * factor
        )

        // Ванадій
        val V_P = V_G * (100 - W_P) / 100

        // output
        result = """
            Склад робочої маси:
            Вуглець: %.2f%%
            Водень: %.2f%%
            Кисень: %.2f%%
            Сірка: %.2f%%
            Ванадій: %.2f мг/кг
            Нижня теплота згоряння: %.2f МДж/кг
        """.trimIndent().format(workMass["C"], workMass["H"], workMass["O"], workMass["S"], V_P, workMass["Q"])
    }

    Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Dynamically create TextFields for each input field
        fields.forEach { (label, field) ->
            InputField(
                label = label,
                value = inputValues[field] ?: "",
                onValueChange = { newValue ->
                    inputValues = inputValues + (field to newValue)
                }
            )
        }

        Button(onClick = { calculateSecondTask() }, modifier = Modifier.fillMaxWidth()) {
            Text("Розрахувати")
        }

        Button(onClick = onBackPressed, modifier = Modifier.fillMaxWidth()) {
            Text("Повернутись")
        }

        Text(text = result, modifier = Modifier.padding(top = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SecondTaskPreview() {
    Lab1Theme {
        SecondTaskApp(onBackPressed = {})
    }
}