package com.example.cosa.presentation.ui.helper

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cosa.data.database.AppDatabase
import com.example.cosa.data.repository.UsuarioRepository
import com.example.cosa.presentation.viewmodel.UsuarioViewModel
import com.example.cosa.presentation.viewmodel.UsuarioViewModelFactory

@Composable
fun rememberUsuarioViewModel(): UsuarioViewModel {
    val context = LocalContext.current
    val usuarioDao = remember { AppDatabase.getDatabase(context).usuarioDao() }
    val usuarioRepository = remember { UsuarioRepository(usuarioDao) }
    val factory = remember { UsuarioViewModelFactory(usuarioRepository) }
    return viewModel(factory = factory)
}