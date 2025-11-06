package com.example.cosa.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosa.data.Repository.ProductoRepository
import com.example.cosa.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(
    private val repository: ProductoRepository = ProductoRepository()
) : ViewModel() {

    private val _productos = MutableStateFlow<List<Producto>>(emptyList())
    val productos: StateFlow<List<Producto>> = _productos

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            _isLoading.value = true
            _productos.value = repository.obtenerProductos()
            _isLoading.value = false
        }
    }

    fun buscarProducto(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _productos.value = repository.buscarProducto(query)
            _isLoading.value = false
        }
    }

    fun filtrarPorCategoria(categoria: com.example.cosa.data.Enum.CategoriaENUM?) {
        viewModelScope.launch {
            _isLoading.value = true
            _productos.value = if (categoria == null) {
                repository.obtenerProductos()
            } else {
                repository.buscarCategoria(categoria)
            }
            _isLoading.value = false
        }
    }
}
