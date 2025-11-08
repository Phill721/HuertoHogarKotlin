package com.example.cosa.presentation.ui.Components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.cosa.data.model.CartItem
import com.example.cosa.presentation.viewmodel.CartViewModel

@Composable
fun CartItemRow(item: CartItem, cartViewModel: CartViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(item.nombre, fontWeight = FontWeight.Bold)
            Text("${item.cantidad} x $${"%.2f".format(item.precio)}")
        }
        Row {
            IconButton(onClick = { cartViewModel.decrease(item.productoId) }) {
                Text("-")
            }
            IconButton(onClick = { cartViewModel.increase(item.productoId) }) {
                Text("+")
            }
            IconButton(onClick = { cartViewModel.removeProduct(item.productoId) }) {
                Text("🗑")
            }
        }
    }
}
