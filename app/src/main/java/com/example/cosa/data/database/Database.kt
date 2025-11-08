package com.example.cosa.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cosa.data.dao.UsuarioDAO
import com.example.cosa.data.model.Usuario


@Database(entities = [Usuario::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDAO
}