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
import com.example.cosa.presentation.viewmodel.SessionViewModel
import com.example.cosa.presentation.viewmodel.CartViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HuertoNavbar(
    navController: NavController? = null,
    sessionViewModel: SessionViewModel,
    cartViewModel: CartViewModel,
    content: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val isLoggedIn by sessionViewModel.isLoggedIn.collectAsState(initial = false)
    val currentUser by sessionViewModel.currentUser.collectAsState(initial = null)

    var showCart by remember { mutableStateOf(false) }
    val itemCount by cartViewModel.itemCount.collectAsState()

    // 🔥 Determinar si es admin por correo
    val isAdmin = currentUser?.correo?.endsWith("@profesor.duoc.cl") == true

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
                    scope.launch { drawerState.close() }
                }
                DrawerButton("Blogs") {
                    navController?.navigate("blogs")
                    scope.launch { drawerState.close() }
                }
                DrawerButton("Contacto") {
                    navController?.navigate("contacto")
                    scope.launch { drawerState.close() }
                }

                // ✅ Solo mostrar si el usuario es admin
                if (isAdmin) {
                    DrawerButton("Admin") {
                        navController?.navigate("admin")
                        scope.launch { drawerState.close() }
                    }
                }

                // 🔥 Control de sesión
                if (isLoggedIn) {
                    DrawerButton("Perfil") {
                        navController?.navigate("perfil")
                        scope.launch { drawerState.close() }
                    }
                    DrawerButton("Cerrar sesión") {
                        navController?.let { nav ->
                            sessionViewModel.logout(nav.context)
                        }
                        scope.launch { drawerState.close() }
                        navController?.navigate("home")
                    }
                } else {
                    DrawerButton("Registrarse") {
                        navController?.navigate("registro")
                        scope.launch { drawerState.close() }
                    }
                    DrawerButton("Iniciar sesión") {
                        navController?.navigate("login")
                        scope.launch { drawerState.close() }
                    }
                }

                DrawerButton("Carrito 🛒") {
                    showCart = true
                    scope.launch { drawerState.close() }
                }
            }
        }
    ) {
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
                        IconButton(onClick = { showCart = true }) {
                            BadgedBox(
                                badge = {
                                    if (itemCount > 0) {
                                        Badge {
                                            Text(itemCount.toString())
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.cart1),
                                    contentDescription = "Carrito",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                )
            },
            content = { innerPadding ->
                content(innerPadding)
            }
        )

        if (showCart) {
            CartModal(
                cartViewModel = cartViewModel,
                onClose = { showCart = false },
                onGoToCart = {
                    showCart = false
                    navController?.navigate("carrito")
                }
            )
        }
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
