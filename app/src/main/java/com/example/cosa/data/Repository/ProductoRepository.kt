package com.example.cosa.data.Repository

import com.example.cosa.data.Enum.CategoriaENUM
import com.example.cosa.data.model.Producto
import kotlinx.coroutines.delay

class ProductoRepository {

    private val productos = mutableListOf(
        Producto("fruit1","Manzanas Fuji","Manzanas Fuji crujientes y dulces, cultivadas en el Valle del Maule. Perfectas para meriendas saludables o como ingrediente en postres. Estas manzanas son conocidas por su textura firme y su sabor equilibrado entre dulce y ácido.",
            1200.0,"/","/","/","/",50, CategoriaENUM.FRUTAS_FRESCAS),
        Producto("fruit2","Naranjas Valencianas","Jugosas y ricas en vitamina C, estas naranjas Valencia son ideales para zumos frescos y refrescantes. Cultivadas en condiciones climáticas óptimas que aseguran su dulzura y jugosidad",
            1000.0,"/","/","/","/",50, CategoriaENUM.FRUTAS_FRESCAS)

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

    suspend fun buscarCategoria(categoria: CategoriaENUM): List<Producto>{
        delay(300)
        return productos.filter { it.categoria == categoria }
    }
}