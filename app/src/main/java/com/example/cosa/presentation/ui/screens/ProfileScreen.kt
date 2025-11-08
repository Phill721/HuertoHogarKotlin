package com.example.cosa.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cosa.presentation.ui.Components.HuertoNavbar
import com.example.cosa.presentation.ui.Components.ModalComponent
import com.example.cosa.presentation.viewmodel.SessionViewModel
import com.example.cosa.data.model.Usuario
import com.example.cosa.presentation.viewmodel.CartViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    sessionViewModel: SessionViewModel = viewModel(),
    cartViewModel: CartViewModel
) {
    var showModal by remember { mutableStateOf(false) }
    var modalMessage by remember { mutableStateOf("") }

    // Traemos los datos del usuario de SessionViewModel
    val user by sessionViewModel.currentUser.collectAsState()

    // Estados locales para editar
    var username by remember(user) { mutableStateOf(user?.usuario ?: "") }
    var password by remember { mutableStateOf("") }

    HuertoNavbar(
        navController = navController,
        sessionViewModel = sessionViewModel,
        cartViewModel = cartViewModel
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                "Mi Perfil",
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                color = androidx.compose.ui.graphics.Color(0xFF2E8B57)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                    // Correo
                    OutlinedTextField(
                        value = user?.correo ?: "",
                        onValueChange = {},
                        label = { Text("Correo electrónico") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true
                    )

                    // Nombre de usuario
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Nombre de usuario") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Nueva contraseña
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Nueva contraseña") },
                        placeholder = { Text("Ingresa una nueva contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation()
                    )

                    // Botón guardar cambios
                    Button(
                        onClick = {
                            // Lógica de actualización
                            sessionViewModel.updateUser(
                                updatedUsername = username,
                                updatedPassword = if (password.isNotBlank()) password else null
                            ) { success ->
                                modalMessage = if (success) "Perfil actualizado correctamente" else "Error al actualizar"
                                showModal = true
                                password = "" // Limpiar input de password
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.ui.graphics.Color(0xFF2E8B57))
                    ) {
                        Text("Guardar cambios", color = androidx.compose.ui.graphics.Color.White)
                    }
                }
            }

            // Modal de feedback
            ModalComponent(
                show = showModal,
                title = "Perfil",
                message = modalMessage,
                onClose = { showModal = false }
            )
        }
    }
}
