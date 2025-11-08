package com.example.cosa.presentation.ui.Components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.cosa.presentation.viewmodel.CartViewModel

@Composable
fun CartModal(
    cartViewModel: CartViewModel,
    onClose: () -> Unit,
    onGoToCart: () -> Unit
) {
    val items = cartViewModel.items // mutableStateList -> recomposes automatically
    val total = cartViewModel.total()

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text("Tu carrito") },
        text = {
            Column {
                if (items.isEmpty()) {
                    Text("Tu carrito está vacío.")
                } else {
                    Column {
                        items.forEach { it ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(it.nombre, fontWeight = FontWeight.Bold)
                                    Text("${it.cantidad} x $${"%.2f".format(it.precio)}")
                                }
                                Row {
                                    IconButton(onClick = { cartViewModel.decrease(it.productoId) }) {
                                        Text("-")
                                    }
                                    IconButton(onClick = { cartViewModel.increase(it.productoId) }) {
                                        Text("+")
                                    }
                                    IconButton(onClick = { cartViewModel.removeProduct(it.productoId) }) {
                                        Text("🗑")
                                    }
                                }
                            }
                            Divider()
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Total: $${"%.2f".format(total)}", fontWeight = FontWeight.Bold)
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onGoToCart() // 🔥 aquí navegás a CartScreen
                }
            ) {
                Text("Ir al carrito")
            }
        },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text("Cerrar")
            }
        }
    )
}
