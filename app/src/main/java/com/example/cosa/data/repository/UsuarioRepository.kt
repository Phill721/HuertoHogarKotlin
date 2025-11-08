package com.example.cosa.data.repository

import com.example.cosa.data.dao.UsuarioDAO
import com.example.cosa.data.model.Usuario

class UsuarioRepository(private val usuarioDAO: UsuarioDAO) {
    suspend fun registrar(usuario: Usuario){
        usuarioDAO.insert(usuario)
    }
    suspend fun existeCorreo(correo: String): Boolean {
        return usuarioDAO.getUsuarioPorCorreo(correo) != null
    }
    suspend fun existeUsuario(usuario: String): Boolean {
        return usuarioDAO.getUsuarioPorNombre(usuario) != null
    }
}