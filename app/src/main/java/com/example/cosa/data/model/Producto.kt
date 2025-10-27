package com.example.cosa.data.model

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stock: Int,
    val categoria: String
) {
    val disponible:Boolean
        get() = stock > 0

    val precioFormateado
        get() = "$%.2f".format(precio)
}