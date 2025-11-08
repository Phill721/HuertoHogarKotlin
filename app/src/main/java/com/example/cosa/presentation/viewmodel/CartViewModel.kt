package com.example.cosa.presentation.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.cosa.data.model.CartItem
import com.example.cosa.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartViewModel : ViewModel() {

    private val _items = mutableStateListOf<CartItem>()
    val items: List<CartItem> get() = _items

    private val _count = MutableStateFlow(0)
    val itemCount: StateFlow<Int> = _count

    fun addProduct(cartItem: CartItem) {
        val existing = _items.indexOfFirst { it.productoId == cartItem.productoId }
        if (existing >= 0) {
            _items[existing].cantidad += cartItem.cantidad
        } else {
            _items.add(cartItem)
        }
        recomputeCount()
    }

    fun addProductFromProducto(producto: Producto) {
        // adapta según tu modelo Producto
        addProduct(
            CartItem(
                productoId = producto.id,
                nombre = producto.nombre,
                precio = producto.precio,
                cantidad = 1,
                imagenResName = producto.imagen1 // opcional
            )
        )
    }

    fun removeProduct(productoId: String) {
        _items.removeAll { it.productoId == productoId }
        recomputeCount()
    }

    fun increase(productoId: String) {
        _items.find { it.productoId == productoId }?.let {
            it.cantidad += 1
            recomputeCount()
        }
    }

    fun decrease(productoId: String) {
        val it = _items.find { it.productoId == productoId } ?: return
        it.cantidad -= 1
        if (it.cantidad <= 0) _items.remove(it)
        recomputeCount()
    }

    fun clearCart() {
        _items.clear()
        recomputeCount()
    }

    fun total(): Double {
        return _items.sumOf { it.precio * it.cantidad }
    }

    private fun recomputeCount() {
        _count.value = _items.sumOf { it.cantidad }
    }
}