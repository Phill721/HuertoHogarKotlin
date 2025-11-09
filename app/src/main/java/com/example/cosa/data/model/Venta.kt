package com.example.cosa.data.model

import java.util.Date

data class SaleItem(
    val productoId: String,
    val nombre: String,
    val cantidad: Int,
    val precioUnitario: Double
)

data class Sale(
    val id: String,
    val fecha: Long = Date().time,
    val total: Double,
    val items: List<SaleItem>
)