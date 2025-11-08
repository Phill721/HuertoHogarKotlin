package com.example.cosa.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.cosa.data.model.Sale
import com.example.cosa.data.model.SaleItem
import com.example.cosa.data.repository.VentaRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

private val AccentGreen = Color(0xFF2E8B57)
private val BgMain = Color(0xFFF7F7F7)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentasAdminScreen(navController: NavHostController) {
    val repo = remember { VentaRepository() }
    val scope = rememberCoroutineScope()

    var ventas by remember { mutableStateOf<List<Sale>>(emptyList()) }
    var totalIngresos by remember { mutableStateOf(0.0) }
    var totalVentas by remember { mutableStateOf(0) }
    var totalProductosVendidos by remember { mutableStateOf(0) }

    var ventaSeleccionada by remember { mutableStateOf<Sale?>(null) }
    var mostrarDetalle by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        ventas = repo.obtenerVentas()
        totalVentas = ventas.size
        totalIngresos = ventas.sumOf { it.total }
        totalProductosVendidos = ventas.sumOf { it.items.sumOf { it.cantidad } }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .background(BgMain)
        .padding(16.dp)) {

        Text("Ventas", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))

        // Mini dashboard: 3 tarjetas
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Card(modifier = Modifier.weight(1f), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Total Ventas", fontWeight = FontWeight.SemiBold)
                    Text(totalVentas.toString(), fontSize = 20.sp, color = AccentGreen)
                }
            }
            Card(modifier = Modifier.weight(1f), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Ingresos", fontWeight = FontWeight.SemiBold)
                    Text("$${totalIngresos.toInt()}", fontSize = 20.sp, color = AccentGreen)
                }
            }
            Card(modifier = Modifier.weight(1f), elevation = CardDefaults.cardElevation(6.dp)) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text("Productos vendidos", fontWeight = FontWeight.SemiBold)
                    Text(totalProductosVendidos.toString(), fontSize = 20.sp, color = AccentGreen)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(4.dp)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text("Historial de Ventas", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(ventas) { v ->
                        VentaListItem(v, onDetalle = { s ->
                            ventaSeleccionada = s
                            mostrarDetalle = true
                        })
                        HorizontalDivider(thickness = 1.dp)
                    }
                }
            }
        }
    }

    if (mostrarDetalle && ventaSeleccionada != null) {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("es","ES"))
        val v = ventaSeleccionada!!
        AlertDialog(
            onDismissRequest = { mostrarDetalle = false },
            confirmButton = {
                TextButton(onClick = { mostrarDetalle = false }) { Text("Cerrar") }
            },
            title = { Text("Detalle venta ${v.id}") },
            text = {
                Column {
                    Text("Fecha: ${sdf.format(java.util.Date(v.fecha))}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Total: $${v.total.toInt()}", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Items:")
                    v.items.forEach { item ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("${item.nombre} x${item.cantidad}")
                            Text("$${(item.precioUnitario*item.cantidad).toInt()}")
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun VentaListItem(v: Sale, onDetalle: (Sale) -> Unit) {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es","ES"))
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier.weight(1f)) {
            Text("Venta ${v.id}", fontWeight = FontWeight.SemiBold)
            Text(sdf.format(java.util.Date(v.fecha)))
        }
        Text("$${v.total.toInt()}")
        Spacer(modifier = Modifier.width(12.dp))
        Button(onClick = { onDetalle(v) }, colors = ButtonDefaults.buttonColors(containerColor = AccentGreen)) {
            Text("Detalle", color = Color.White)
        }
    }
}
