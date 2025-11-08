package com.example.cosa.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cosa.presentation.ui.Components.HuertoNavbar
import com.example.cosa.presentation.ui.helper.rememberUsuarioViewModel

@Composable
fun LoginScreen(navController: NavController) {

    // 💡 Obtener el ViewModel usando el helper
    val usuarioViewModel = rememberUsuarioViewModel()

    HuertoNavbar(navController = navController) { innerPadding ->

        var correo by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var mensaje by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8F5E9)) // Fondo verde suave 🌿
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(0.9f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // Fondo blanco del card
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Inicio de sesión para clientes",
                        fontSize = 22.sp,
                        color = Color(0xFF2E7D32), // Verde oscuro
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = mensaje.contains("correo", ignoreCase = true),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF388E3C),
                            focusedLabelColor = Color(0xFF388E3C)
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF388E3C),
                            focusedLabelColor = Color(0xFF388E3C)
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            usuarioViewModel.iniciarSesion(
                                correo,
                                password
                            ) { exito, msg ->
                                mensaje = msg
                                if (exito) {
                                    navController.navigate("home")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E8B57) // Verde principal del botón
                        )
                    ) {
                        Text("Iniciar Sesión", color = Color.White)
                    }

                    if (mensaje.isNotEmpty()) {
                        Text(
                            text = mensaje,
                            color = if (mensaje.contains("exitoso", ignoreCase = true))
                                Color(0xFF2E7D32) // Verde éxito
                            else
                                MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        }
    }
}
