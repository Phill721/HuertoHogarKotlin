package com.example.cosa.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val rut: String,
    val usuario: String,
    val correo: String,
    val pass: String
) {
}