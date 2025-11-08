package com.example.cosa.presentation.ui.Components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier

@Composable
fun ModalComponent(
    show: Boolean,
    title: String,
    message: String,
    onClose: () -> Unit
) {
    if (show) {
        AlertDialog(
            onDismissRequest = onClose,
            containerColor = Color(0xFF2E8B57), // fondo verde del modal
            title = { Text(title, color = Color.White) }, // título blanco
            text = { Text(message, color = Color.White) }, // mensaje blanco
            confirmButton = {
                Button(
                    onClick = onClose,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("OK", color = Color(0xFF2E8B57)) // texto verde
                }
            }
        )
    }
}
