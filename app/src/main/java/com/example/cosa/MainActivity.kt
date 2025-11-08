package com.example.cosa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.cosa.presentation.ui.screens.BlogsScreen
import com.example.cosa.presentation.ui.screens.ContactoScreen
import com.example.cosa.presentation.ui.screens.HomeScreen
import com.example.cosa.presentation.ui.screens.NosotrosScreen
import com.example.cosa.presentation.ui.screens.ProductosScreen
import com.example.cosa.presentation.ui.screens.ProductDetailScreen
import com.example.cosa.presentation.ui.screens.RegisterScreen
import com.example.cosa.presentation.viewmodel.ProductoViewModel
import com.example.cosa.presentation.viewmodel.UsuarioViewModel
import com.example.cosa.presentation.viewmodel.UsuarioViewModelFactory
import com.example.cosa.ui.theme.CosaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CosaTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Pantalla principal
        composable("home") {
            HomeScreen(navController = navController)
        }

        // Lista de productos
        composable("productos") {
            val productoViewModel: ProductoViewModel = viewModel()
            ProductosScreen(viewModel = productoViewModel, navController = navController)
        }

        // Detalle de producto
        composable("producto/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            if (id != null) {
                ProductDetailScreen(navController = navController, productoId = id)
            }
        }
        // Blogs
        composable("blogs") {
            BlogsScreen(navController = navController)
        }
        // Seccion nosotros
        composable("nosotros") {
            NosotrosScreen(navController = navController)
        }
        // Contacto
        composable("contacto"){
            ContactoScreen(navController = navController)
        }
        composable("registro") {
            val context = LocalContext.current

            // 1️⃣ Crear la base de datos Room (una sola vez con remember)
            val db = remember {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app-db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }

            // 2️⃣ Crear DAO y repositorio
            val usuarioDao = remember { db.usuarioDao() }
            val repo = remember { UsuarioRepository(usuarioDao) }

            // 3️⃣ Crear la factory del ViewModel
            val factory = remember { UsuarioViewModelFactory(repo) }

            // 4️⃣ Obtener el ViewModel usando la factory
            val usuarioViewModel: UsuarioViewModel = viewModel(factory = factory)

            // 5️⃣ Pasarlo al RegisterScreen
            RegisterScreen(navController = navController, usuarioViewModel = usuarioViewModel)
        }
    }
}
