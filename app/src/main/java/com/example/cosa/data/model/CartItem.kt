package com.example.cosa.data.model

data class CartItem(
    val productoId: String,
    val nombre: String,
    val precio: Double,
    var cantidad: Int = 1,
    val imagenResName: String? = null
)