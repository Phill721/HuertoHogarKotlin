package com.example.cosa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cosa.presentation.ui.screens.HomeScreen
import com.example.cosa.presentation.ui.screens.ProductosScreen
import com.example.cosa.presentation.viewmodel.ProductoViewModel
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

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }

        composable("productos") {
            val productoViewModel: ProductoViewModel = viewModel()
            ProductosScreen(viewModel = productoViewModel, navController = navController)
        }
    }
}
