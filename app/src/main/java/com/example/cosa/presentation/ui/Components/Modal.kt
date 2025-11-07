package com.example.cosa.presentation.ui.Components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

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
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                Button(onClick = onClose) {
                    Text("OK")
                }
            }
        )
    }
}