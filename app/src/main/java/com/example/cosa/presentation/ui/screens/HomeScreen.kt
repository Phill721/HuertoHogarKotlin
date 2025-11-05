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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cosa.presentation.viewmodel.ProductoViewModel
import com.example.cosa.R
import com.example.cosa.data.model.Producto
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid


@Composable
fun HomeScreen(
    viewModel: ProductoViewModel = viewModel(),
    onNavigateToProductos: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    val productos by viewModel.productos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Scaffold(
        topBar = { HomeTopBar(onNavigateToLogin, onNavigateToProductos) },
        containerColor = Color(0xFFF5F5F5)
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF2E8B57))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                item {
                    HeroSection(onNavigateToProductos)
                    Spacer(modifier = Modifier.height(16.dp))
                    ProductosMasCompradosTitle()
                }

                items(productos) { producto ->
                    ProductoCard(producto)
                }

                item {
                    FooterSection()
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeTopBar(onLoginClick: () -> Unit, onProductosClick: () -> Unit) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.iconmain),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp)
                )
                Text("Tienda Huerto Hogar", fontWeight = FontWeight.Bold)
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF2E8B57),
            titleContentColor = Color.White
        ),
        actions = {
            TextButton(onClick = onProductosClick) {
                Text("Productos", color = Color.White)
            }
            TextButton(onClick = onLoginClick) {
                Text("Iniciar sesión", color = Color.White)
            }
        }
    )
}

@Composable
fun HeroSection(onProductosClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2E8B57))
            .padding(16.dp)
    ) {
        Column {
            Text(
                "Desde nuestro Huerto Hogar para ti",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "HuertoHogar es una tienda online dedicada a llevar la frescura y calidad del campo a tu hogar en Chile.",
                color = Color.White,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onProductosClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) {
                Text("Ver productos", color = Color(0xFF2E8B57))
            }
        }
    }
}

@Composable
fun ProductosMasCompradosTitle() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2E8B57))
            .padding(12.dp)
    ) {
        Text(
            text = "Productos más comprados!",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProductoCard(producto: Producto) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fruit1), // temporal hasta tener imágenes reales
                contentDescription = producto.nombre,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(producto.nombre, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(producto.descripcion, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57))
            ) {
                Text("Comprar: ${producto.precioFormateado}", color = Color.White)
            }
        }
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
        Text("© 2025 HuertoHogar. Todos los derechos reservados.", color = Color.White, fontSize = 12.sp)
    }
}

