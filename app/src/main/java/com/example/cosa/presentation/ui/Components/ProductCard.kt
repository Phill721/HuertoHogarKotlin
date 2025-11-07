// File: ProductoCard.kt
package com.example.cosa.presentation.ui.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cosa.data.model.Producto

@Composable
fun ProductoCard(producto: Producto, navController: NavController) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            // Imagen principal del producto
            val imageResId = context.resources.getIdentifier(
                producto.imagen1,
                "drawable",
                context.packageName
            )

            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = producto.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre del producto
            Text(
                producto.nombre,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            // Descripción corta
            Text(
                producto.descripcion,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón de compra
            Button(
                onClick = {
                    navController.navigate("producto/${producto.id}")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E8B57)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Comprar: ${producto.precioFormateado}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
