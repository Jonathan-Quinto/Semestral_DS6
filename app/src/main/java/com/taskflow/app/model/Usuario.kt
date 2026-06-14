package com.taskflow.app.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidad que representa un usuario registrado en TaskFlow.
 *
 * Roles:
 *  - "LIDER"       → puede crear tareas, asignarlas y ver estadísticas globales
 *  - "PARTICIPANTE" → solo ve las tareas que le fueron asignadas
 *
 * El email es único (índice único en BD) para evitar registros duplicados.
 * La contraseña se guarda hasheada con SHA-256, nunca en texto plano.
 */
@Entity(
    tableName = "usuarios",
    indices = [Index(value = ["email"], unique = true)]
)
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nombre: String,

    val email: String,

    // Contraseña hasheada con SHA-256 — ver SesionManager.hashPassword()
    val passwordHash: String,

    // "LIDER" o "PARTICIPANTE"
    val rol: String,

    // Fecha de registro en formato "yyyy-MM-dd HH:mm"
    val fechaRegistro: String = ""
)
