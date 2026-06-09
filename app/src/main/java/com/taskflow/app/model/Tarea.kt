package com.taskflow.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad principal que representa una tarea en la base de datos.
 * Cada campo tiene un propósito claro para el CRUD completo.
 */
@Entity(tableName = "tareas")
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
    val fechaCreacion: String = ""
)
