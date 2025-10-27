package com.example.cosa.data.Repository

import com.example.cosa.data.Enum.CategoriaENUM
import com.example.cosa.data.model.Producto
import kotlinx.coroutines.delay

class ProductoRepository {

    private val productos = mutableListOf(
        Producto("fruit1","Manzanas Fuji","Manzanas Fuji crujientes y dulces, cultivadas en el Valle del Maule. Perfectas para meriendas saludables o como ingrediente en postres. Estas manzanas son conocidas por su textura firme y su sabor equilibrado entre dulce y ácido.",
            1200.0,"/","/","/","/",50, CategoriaENUM.FRUTAS_FRESCAS),
        Producto("fruit2","Naranjas Valencianas","Jugosas y ricas en vitamina C, estas naranjas Valencia son ideales para zumos frescos y refrescantes. Cultivadas en condiciones climáticas óptimas que aseguran su dulzura y jugosidad",
            1000.0,"/","/","/","/",50, CategoriaENUM.FRUTAS_FRESCAS),
        Producto("fruit3","Platanos Cavendish","Plátanos maduros y dulces, perfectos para el desayuno o como snack energético. Estos plátanos son ricos en potasio y vitaminas, ideales para mantener una dieta equilibrada.",
            800.0,"/","/","/","/",50, CategoriaENUM.FRUTAS_FRESCAS),
        Producto("verdura1","Zanahorias Organicas","Zanahorias crujientes cultivadas sin pesticidas en la Región de O'Higgins. Excelente fuente de vitamina A y fibra, ideales para ensaladas, jugos o como snack saludable.",
            900.0,"/","/","/","/",50, CategoriaENUM.VERDURAS_ORGANICAS),
        Producto("verdura2","Espinacas Frescas","Espinacas frescas y nutritivas, perfectas para ensaladas y batidos verdes. Estas espinacas son cultivadas bajo prácticas orgánicas que garantizan su calidad y valor nutricional",
            700.0,"/","/","/","/",50, CategoriaENUM.VERDURAS_ORGANICAS),
        Producto("verdura3","Pimientos Tricolores","Pimientos rojos, amarillos y verdes, ideales para salteados y platos coloridos. Ricos en antioxidantes y vitaminas, estos pimientos añaden un toque vibrante y saludable a cualquier receta",
            1500.0,"/","/","/","/",50, CategoriaENUM.VERDURAS_ORGANICAS),
        Producto("organicproduct1","Miel Organica","Miel pura y orgánica producida por apicultores locales. Rica en antioxidantes y con un sabor inigualable, perfecta para endulzar de manera natural tus comidas y bebidas.",
            5000.0,"/","/","/","/",50, CategoriaENUM.PRODUCTOS_ORGANICOS)


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