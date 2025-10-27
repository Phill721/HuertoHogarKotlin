package com.example.cosa.data.Repository

import com.example.cosa.data.model.Producto
import kotlinx.coroutines.delay

class ProductoRepository {

    private val productos = mutableListOf(
        Producto(1,"GeForce RTX 4060 Asus Dual","Tarjeta grafica de gama media",
            350000.00,50,"Hardware"),
        Producto(2,"Intel Core i7-14700","Procesador de gama alta para PCs",
            400000.00,30,"Hardware"),
        Producto(3,"RAM 16GB (2X8) DDR4 3400mhz Kingston HyperX","Memoria RAM de alta velocidad",
            120000.00,50,"Hardware")

    )
    suspend fun obtenerProductos(): List<Producto>{
        delay(1000)
        return productos.toList()
    }

    suspend fun buscarProducto(query: String): List<Producto> {
        delay(600)
        return productos.filter {
            it.nombre.contains(query, ignoreCase = true) ||
            it.descripcion.contains(query, ignoreCase = true)
        }
    }

    suspend fun buscarCategoria(categoria: String): List<Producto>{
        delay(300)
        return productos.filter { it.categoria == categoria }
    }
}