package com.example.cosa.data.repository

import com.example.cosa.data.model.Sale
import com.example.cosa.data.model.SaleItem
import java.util.Date

class VentaRepository {
    private val ventas = mutableListOf<Sale>()

    init {
        // datos de ejemplo
        ventas.add(
            Sale(
                "v1",
                Date().time,
                3200.0,
                listOf(
                    SaleItem("fruit1","Manzanas Fuji",2,1200.0),
                    SaleItem("fruit3","Platanos Cavendish",1,800.0)
                )
            )
        )

        ventas.add(
            Sale(
                "v2",
                Date().time - 86400000,
                1500.0,
                listOf(
                    SaleItem("verdura3","Pimientos Tricolores",1,1500.0)
                )
            )
        )

        ventas.add(
            Sale(
                "v3",
                Date().time - 2*86400000,
                4200.0,
                listOf(
                    SaleItem("organicproduct1","Miel Organica",1,4200.0)
                )
            )
        )
    }

    fun obtenerVentas(): List<Sale> {
        return ventas.toList()
    }

    fun obtenerVentaPorId(id: String): Sale? {
        return ventas.find { it.id == id }
    }
}
