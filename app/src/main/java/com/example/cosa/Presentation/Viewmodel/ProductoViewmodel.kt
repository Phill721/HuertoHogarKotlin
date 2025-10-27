package com.example.cosa.Presentation.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cosa.data.Repository.ProductoRepository
import com.example.cosa.data.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductoViewmodel : ViewModel() {
    private val repository = ProductoRepository()
    private val _uiState = MutableStateFlow(ProductoUIState())
    val uiState: StateFlow<ProductoUIState> = _uiState.asStateFlow()
    init {
        cargarProductos()
    }

    fun cargarProductos(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = "Nada")
            try {
                val productos = repository.obtenerProductos()
                _uiState.value = _uiState.value.copy(
                    productos = productos,
                    isLoading = false
                )
            } catch (e: Exception){
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar productos: ${e.message}"
                )
            }
        }
    }

    fun buscarPortexto(query: String){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val productos = if (query.isBlank()){
                    repository.obtenerProductos()
                } else {
                    repository.buscarProducto(query)
                }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    productos = productos
                )
            } catch (e: Exception){
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Error en la busqueda"
                )
            }
        }
    }
}

data class ProductoUIState(
    val productos: List<Producto> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null

)