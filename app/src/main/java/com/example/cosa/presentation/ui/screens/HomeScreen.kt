package com.example.cosa.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.cosa.presentation.ui.Components.HuertoNavbar
import com.example.cosa.presentation.ui.Components.ProductoCard
import com.example.cosa.presentation.viewmodel.ProductoViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: ProductoViewModel = viewModel()
    val productos by viewModel.productos.collectAsState(initial = emptyList())
    val isLoading by viewModel.isLoading.collectAsState(initial = true)

    HuertoNavbar(navController = navController) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(innerPadding)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF2E8B57))
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    verticalArrangement = Arrangement.spacedBy(0.dp), // sin espacios entre secciones
                    contentPadding = PaddingValues(0.dp)
                ) {
                    item {
                        HeroSection(onProductosClick = { navController.navigate("productos") })
                        ProductosMasCompradosTitle()
                    }

                    items(productos.take(3)) { producto ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            ProductoCard(producto = producto, navController = navController)
                        }
                    }

                    item {
                        FooterSection()
                    }
                }
            }
        }
    }
}

@Composable
fun HeroSection(onProductosClick: () -> Unit) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2E8B57))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            ) {
                Text(
                    "Desde nuestro Huerto Hogar para ti",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "HuertoHogar es una tienda online dedicada a llevar la frescura y calidad del campo directamente a tu mesa. ¡Más de 6 años cultivando bienestar en Chile!",
                    color = Color.White,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onProductosClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                ) {
                    Text("Ver productos", color = Color(0xFF2E8B57))
                }
            }

            Image(
                painter = painterResource(
                    id = context.resources.getIdentifier(
                        "banner1",
                        "drawable",
                        context.packageName
                    )
                ),
                contentDescription = "Huerto Hogar",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun ProductosMasCompradosTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2E8B57))
            .padding(vertical = 12.dp)
    ) {
        Text(
            text = "🌿 Productos más comprados 🌿",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun FooterSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2E8B57))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("HuertoHogar", color = Color.White, fontWeight = FontWeight.Bold)
        Text("Frescura natural desde el huerto directa a tu mesa", color = Color.White)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "© 2025 HuertoHogar. Todos los derechos reservados.",
            color = Color.White,
            fontSize = 12.sp
        )
    }
}
