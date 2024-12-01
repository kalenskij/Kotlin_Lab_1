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

class FirstActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab1Theme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    FirstTaskApp(
                        modifier = Modifier.padding(innerPadding),
                        onBackPressed = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
fun FirstTaskApp(modifier: Modifier = Modifier, onBackPressed: () -> Unit) {
    // State for user input
    var inputValues by remember { mutableStateOf(mapOf<String, String>()) }
    var result by remember { mutableStateOf("") }

    val fields = listOf(
        "Вуглець (C)" to "carbon",
        "Водень (H)" to "hydrogen",
        "Кисень (O)" to "oxygen",
        "Сірка (S)" to "sulfur",
        "Вологість (W)" to "moisture",
        "Зольність (A)" to "ash",
        "Азот (N)" to "nitrogen"
    )

    // Helper function to parse input to Double or return 0.0 if invalid
    fun parseInput(input: String?): Double {
        return input?.toDoubleOrNull() ?: 0.0
    }

    // Function to calculate the result
    fun calculateFirstTask() {
        val H_P = parseInput(inputValues["hydrogen"])
        val C_P = parseInput(inputValues["carbon"])
        val S_P = parseInput(inputValues["sulfur"])
        val N_P = parseInput(inputValues["nitrogen"])
        val O_P = parseInput(inputValues["oxygen"])
        val W_P = parseInput(inputValues["moisture"])
        val A_P = parseInput(inputValues["ash"])

        // Constants
        val K_RS = 100 / (100 - W_P)
        val K_RG = 100 / (100 - W_P - A_P)


        // Dry mass and fuel mass calculations
        val dryMass = mapOf(
            "H" to H_P * K_RS,
            "C" to C_P * K_RS,
            "S" to S_P * K_RS,
            "N" to N_P * K_RS,
            "O" to O_P * K_RS
        )

        val fuelMass = mapOf(
            "H" to H_P * K_RG,
            "C" to C_P * K_RG,
            "S" to S_P * K_RG,
            "N" to N_P * K_RG,
            "O" to O_P * K_RG
        )

        // Lower calorific value
        val Q_RH = 339 * C_P + 1030 * H_P - 108.8 * (O_P - S_P) - 25 * W_P

        // Format the result
        result = """
            Склад сухої маси:
            H: %.2f%%, C: %.2f%%, S: %.2f%%, N: %.2f%%, O: %.2f%%
            Склад паливної маси:
            H: %.2f%%, C: %.2f%%, S: %.2f%%, N: %.2f%%, O: %.2f%%
            Нижня теплота згоряння (робоча маса): %.4f МДж/кг
        """.trimIndent().format(
            dryMass["H"], dryMass["C"], dryMass["S"], dryMass["N"], dryMass["O"],
            fuelMass["H"], fuelMass["C"], fuelMass["S"], fuelMass["N"], fuelMass["O"],
            Q_RH
        )
    }

    Column(modifier = modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Manually declare and bind each TextField to its respective state variable
        fields.forEach { (label, field) ->
            InputField(
                label = label,
                value = inputValues[field] ?: "",
                onValueChange = { newValue -> inputValues = inputValues + (field to newValue) }
            )
        }

        Button(onClick = { calculateFirstTask() }, modifier = Modifier.fillMaxWidth()) {
            Text("Розрахувати")
        }

        Button(onClick = onBackPressed, modifier = Modifier.fillMaxWidth()) {
            Text("Повернуться")
        }

        Text(text = result, modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
fun InputField(label: String, value: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun FirstTaskPreview() {
    Lab1Theme {
        FirstTaskApp(onBackPressed = {})
    }
}