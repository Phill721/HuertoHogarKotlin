package com.example.cosa.presentation.ui.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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