package com.example.cosa.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cosa.R
import com.example.cosa.presentation.ui.Components.HuertoNavbar
import com.example.cosa.presentation.ui.Components.ModalComponent
import com.example.cosa.presentation.viewmodel.SessionViewModel

@Composable
fun ContactoScreen(navController: NavController, sessionViewModel: SessionViewModel) {
    var nombre by remember { mutableStateOf(TextFieldValue("")) }
    var correo by remember { mutableStateOf(TextFieldValue("")) }
    var contenido by remember { mutableStateOf(TextFieldValue("")) }

    var showModal by remember { mutableStateOf(false) }
    var modalTitle by remember { mutableStateOf("") }
    var modalMessage by remember { mutableStateOf("") }

    // Validación simple de correo
    fun esCorreoValido(email: String): Boolean {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$")
        return email.matches(regex)
    }

    // Usamos el HuertoNavbar que provee innerPadding
    HuertoNavbar(navController = navController, sessionViewModel = sessionViewModel) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // importante
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título y logo
                Text(
                    text = "Huerto Hogar",
                    fontSize = 28.sp,
                    color = Color(0xFF2E8B57),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.iconmain),
                    contentDescription = "Logo Huerto Hogar",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(bottom = 24.dp)
                )

                // Card del formulario
                Card(
                    shape = RoundedCornerShape(15.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Contáctanos",
                            fontSize = 22.sp,
                            color = Color(0xFF2E8B57),
                            modifier = Modifier.padding(bottom = 20.dp)
                        )

                        // Nombre completo
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre completo") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Correo
                        OutlinedTextField(
                            value = correo,
                            onValueChange = { correo = it },
                            label = { Text("Correo") },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Contenido
                        OutlinedTextField(
                            value = contenido,
                            onValueChange = { contenido = it },
                            label = { Text("Contenido") },
                            modifier = Modifier.fillMaxWidth(),
                            maxLines = 4
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        // Botón enviar
                        Button(
                            onClick = {
                                if (nombre.text.isBlank() || correo.text.isBlank() || contenido.text.isBlank()) {
                                    modalTitle = "Error"
                                    modalMessage = "Por favor completa todos los campos."
                                } else if (!esCorreoValido(correo.text)) {
                                    modalTitle = "Correo inválido"
                                    modalMessage = "Por favor ingresa un correo electrónico válido."
                                } else {
                                    modalTitle = "Éxito"
                                    modalMessage = "Tu mensaje ha sido enviado correctamente."
                                    // limpia los campos
                                    nombre = TextFieldValue("")
                                    correo = TextFieldValue("")
                                    contenido = TextFieldValue("")
                                }
                                showModal = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Enviar Mensaje", color = Color.White)
                        }
                    }
                }
            }
        }
    }

    // Modal de confirmación o error
    ModalComponent(
        show = showModal,
        title = modalTitle,
        message = modalMessage,
        onClose = { showModal = false }
    )
}
