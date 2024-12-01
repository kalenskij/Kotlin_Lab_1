package com.example.lab1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.lab1.ui.theme.Lab1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab1Theme {
                // Access the current context for starting new activities
                val context = LocalContext.current

                // Column layout for buttons
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                ) {
                    // Button for launching FirstActivity
                    NavigationButton(
                        label = "Завдання 1",
                        onClick = {
                            val intent = Intent(context, FirstActivity::class.java)
                            context.startActivity(intent)
                        }
                    )

                    // Button for launching SecondActivity
                    NavigationButton(
                        label = "Завдання 2",
                        onClick = {
                            val intent = Intent(context, SecondActivity::class.java)
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationButton(label: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(label)
    }
}