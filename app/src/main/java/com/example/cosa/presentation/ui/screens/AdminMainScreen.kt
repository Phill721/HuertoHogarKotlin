package com.example.cosa.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cosa.presentation.viewmodel.SessionViewModel

@Composable
fun AdminMainScreen(navController: NavHostController, sessionViewModel: SessionViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("Panel de Administrador", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { navController.navigate("admin/productos") }, modifier = Modifier.fillMaxWidth()) {
            Text("Productos")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("admin/usuarios") }, modifier = Modifier.fillMaxWidth()) {
            Text("Usuarios")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { navController.navigate("admin/ventas") }, modifier = Modifier.fillMaxWidth()) {
            Text("Ventas")
        }
    }
}
