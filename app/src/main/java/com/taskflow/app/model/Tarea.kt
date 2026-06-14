package com.taskflow.app.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Entidad principal que representa una tarea en la base de datos.
 *
 * Campos nuevos para el sistema de roles:
 *  - creadaPor   → ID del usuario (Líder) que creó la tarea
 *  - asignadoA   → ID del usuario (Participante) al que se le asigna
 *                  Si es null, la tarea no está asignada a nadie todavía
 *  - fechaLimite → Fecha límite opcional en "yyyy-MM-dd"
 *
 * ForeignKeys garantizan integridad referencial con la tabla usuarios.
 */
@Entity(
    tableName = "tareas",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["creadaPor"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["asignadoA"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index("creadaPor"),
        Index("asignadoA")
    ]
)
data class Tarea(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // Título obligatorio de la tarea
    val titulo: String,

    // Descripción opcional
    val descripcion: String = "",

    // Prioridad: "Alta", "Media", "Baja"
    val prioridad: String = "Media",

    // Categoría: "Personal", "Trabajo", "Estudios", "Compras"
    val categoria: String = "Personal",

    // Estado: false = pendiente, true = completada
    val completada: Boolean = false,

    // Fecha de creación en formato "yyyy-MM-dd HH:mm"
    val fechaCreacion: String = "",

    // Fecha límite opcional en "yyyy-MM-dd" — vacío si no tiene
    val fechaLimite: String = "",

    // ID del Líder que creó la tarea (0 = sin asignar, para compatibilidad)
    val creadaPor: Int = 0,

    // ID del Participante asignado — null si aún no está asignada
    val asignadoA: Int? = null
)
