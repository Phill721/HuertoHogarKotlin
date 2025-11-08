package com.example.cosa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.cosa.data.database.AppDatabase
import com.example.cosa.data.repository.UsuarioRepository
import com.example.cosa.presentation.ui.screens.*
import com.example.cosa.presentation.viewmodel.*
import com.example.cosa.ui.theme.CosaTheme

class MainActivity : ComponentActivity() {

    private val sessionViewModel: SessionViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 Carga la sesión guardada apenas arranca la app
        sessionViewModel.loadSession(this)

        setContent {
            CosaTheme {
                // 👇 Pasamos la instancia única del SessionViewModel
                AppNavigation(
                    sessionViewModel = sessionViewModel,
                    cartViewModel = cartViewModel
                )
            }
        }
    }
}

@Composable
fun AppNavigation(sessionViewModel: SessionViewModel, cartViewModel: CartViewModel) { // 👈 se recibe acá
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // 🏠 Home
        composable("home") {
            HomeScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                cartViewModel = cartViewModel
            )
        }

        // 🛒 Productos
        composable("productos") {
            val productoViewModel: ProductoViewModel = viewModel()
            ProductosScreen(
                viewModel = productoViewModel,
                navController = navController,
                sessionViewModel = sessionViewModel,
                cartViewModel = cartViewModel
            )
        }

        // 🧺 Detalle de producto
        composable("producto/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                ProductDetailScreen(navController = navController, productoId = id, sessionViewModel = sessionViewModel, cartViewModel = cartViewModel)
            }
        }

        // 📰 Blogs
        composable("blogs") {
            BlogsScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                cartViewModel = cartViewModel
            )
        }

        // 👥 Nosotros
        composable("nosotros") {
            NosotrosScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                cartViewModel = cartViewModel
            )
        }

        // ☎️ Contacto
        composable("contacto") {
            ContactoScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                cartViewModel = cartViewModel
            )
        }

        // 🧾 Registro
        composable("registro") {
            val context = LocalContext.current

            val db = remember {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app-db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }

            val usuarioDao = remember { db.usuarioDao() }
            val repo = remember { UsuarioRepository(usuarioDao) }
            val factory = remember { UsuarioViewModelFactory(repo) }
            val usuarioViewModel: UsuarioViewModel = viewModel(factory = factory)

            RegisterScreen(
                navController = navController,
                usuarioViewModel = usuarioViewModel,
                sessionViewModel = sessionViewModel,
                cartViewModel = cartViewModel
            )
        }

        // 🔐 Login
        composable("login") {
            LoginScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                cartViewModel = cartViewModel
            )
        }
    }
}
