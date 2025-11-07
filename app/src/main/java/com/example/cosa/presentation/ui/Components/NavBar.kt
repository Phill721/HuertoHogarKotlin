// File: HuertoNavbar.kt (reemplaza el existente)
package com.example.cosa.presentation.ui.Components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cosa.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HuertoNavbar(
    navController: NavController? = null,
    // content slot: la pantalla que use este navbar se pasa aquí
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color(0xFF2E8B57)
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Menú principal",
                    fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                )

                DrawerButton("Inicio") {
                    navController?.navigate("home")
                    scope.launch { drawerState.close() }
                }
                DrawerButton("Productos") {
                    navController?.navigate("productos")
                    scope.launch { drawerState.close() }
                }
                DrawerButton("Nosotros") {
                    navController?.navigate("nosotros")
                    scope.launch { drawerState.close() } }
                DrawerButton("Blogs") {
                    navController?.navigate("blogs")
                    scope.launch { drawerState.close() } }
                DrawerButton("Contacto") {
                    navController?.navigate("contacto")
                    scope.launch { drawerState.close() } }
                DrawerButton("Iniciar sesión") { scope.launch { drawerState.close() } }
                DrawerButton("Carrito 🛒") { scope.launch { drawerState.close() } }
            }
        }
    ) {
        // Usamos Scaffold como content principal para fijar topBar y pasar padding internamente
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = R.drawable.iconmain),
                                contentDescription = "Logo",
                                tint = Color.Unspecified,
                                modifier = Modifier
                                    .size(30.dp)
                                    .padding(end = 8.dp)
                            )
                            Text(
                                text = "Tienda Huerto Hogar",
                                color = Color.White,
                                fontSize = 18.sp
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF2E8B57)
                    ),
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú",
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        // Si no tenés ic_cart, podés quitar o reemplazar
                        IconButton(onClick = { navController?.navigate("carrito") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.cart1),
                                contentDescription = "Carrito",
                                tint = Color.White
                            )
                        }
                    }
                )
            },
            // el bodyContent se entrega al caller a través del slot `content`
            content = { innerPadding ->
                // Dejamos que la pantalla que use el navbar reciba el padding del scaffold
                content(innerPadding)
            }
        )
    }
}

@Composable
fun DrawerButton(text: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp
        )
    }
}
