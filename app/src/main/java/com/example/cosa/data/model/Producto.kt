package com.example.cosa.data.model

import com.example.cosa.data.Enum.CategoriaENUM

data class Producto(
    val id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen1: String,
    val imagen2 : String,
    val imagen3 : String,
    val imagen4 : String,
    val stock: Int,
    val categoria: CategoriaENUM
) {
    val disponible:Boolean
        get() = stock > 0

    val precioFormateado
        get() = "$%.2f".format(precio)
}