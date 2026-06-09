package com.taskflow.app.data.db

import androidx.room.*
import com.taskflow.app.model.Tarea
import kotlinx.coroutines.flow.Flow

/**
 * DAO de tareas — define todas las operaciones de base de datos disponibles.
 * Usado por las Activities a través del repositorio.
 */
@Dao
interface TareaDao {

    // Insertar nueva tarea
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(tarea: Tarea)

    // Actualizar tarea existente (para editar o marcar como completada)
    @Update
    suspend fun actualizar(tarea: Tarea)

    // Eliminar una tarea
    @Delete
    suspend fun eliminar(tarea: Tarea)

    // Obtener todas las tareas ordenadas: pendientes primero, luego por prioridad
    @Query("""
        SELECT * FROM tareas 
        ORDER BY completada ASC, 
        CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 ELSE 3 END ASC
    """)
    fun obtenerTodas(): Flow<List<Tarea>>

    // Obtener tareas por categoría
    @Query("SELECT * FROM tareas WHERE categoria = :categoria ORDER BY completada ASC")
    fun obtenerPorCategoria(categoria: String): Flow<List<Tarea>>

    // Obtener una tarea por ID (para la pantalla de edición)
    @Query("SELECT * FROM tareas WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Int): Tarea?

    // Conteo total de tareas
    @Query("SELECT COUNT(*) FROM tareas")
    suspend fun contarTotal(): Int

    // Conteo de tareas completadas
    @Query("SELECT COUNT(*) FROM tareas WHERE completada = 1")
    suspend fun contarCompletadas(): Int

    // Conteo por categoría
    @Query("SELECT COUNT(*) FROM tareas WHERE categoria = :categoria")
    suspend fun contarPorCategoria(categoria: String): Int

    // Obtener categorías distintas que tienen tareas
    @Query("SELECT DISTINCT categoria FROM tareas")
    suspend fun obtenerCategorias(): List<String>
}
