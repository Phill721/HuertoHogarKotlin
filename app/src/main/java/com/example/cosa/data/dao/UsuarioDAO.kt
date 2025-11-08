package com.example.cosa.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cosa.data.model.Usuario

@Dao
interface UsuarioDAO {
    @Insert
    suspend fun insert(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE correo = :correo LIMIT 1")
    suspend fun getUsuarioPorCorreo(correo: String): Usuario?

    @Query("SELECT * FROM usuarios WHERE usuario = :usuario LIMIT 1")
    suspend fun getUsuarioPorNombre(usuario: String): Usuario?

}