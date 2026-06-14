package com.taskflow.app.data.db

import androidx.room.*
import com.taskflow.app.model.Tarea
import kotlinx.coroutines.flow.Flow

/**
 * DAO de tareas — define todas las operaciones de base de datos disponibles.
 *
 * Queries nuevas para el sistema de roles:
 *  - obtenerPorLider()       → el Líder ve TODAS las tareas que creó
 *  - obtenerPorParticipante() → el Participante ve SOLO las que le asignaron
 *  - obtenerSinAsignar()     → tareas creadas pero sin participante asignado
 *  - asignarParticipante()   → el Líder asigna una tarea a un participante
 */
@Dao
interface TareaDao {

    // ── CRUD básico ───────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(tarea: Tarea)

    @Update
    suspend fun actualizar(tarea: Tarea)

    @Delete
    suspend fun eliminar(tarea: Tarea)

    @Query("SELECT * FROM tareas WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Int): Tarea?

    // ── Queries por rol ───────────────────────────────────────────────────────

    /**
     * Para el Líder: todas las tareas que él creó, ordenadas por estado y prioridad.
     * El Líder ve el panorama completo de su equipo.
     */
    @Query("""
        SELECT * FROM tareas 
        WHERE creadaPor = :liderId
        ORDER BY completada ASC,
        CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 ELSE 3 END ASC
    """)
    fun obtenerPorLider(liderId: Int): Flow<List<Tarea>>

    /**
     * Para el Participante: solo las tareas que le fueron asignadas.
     */
    @Query("""
        SELECT * FROM tareas 
        WHERE asignadoA = :participanteId
        ORDER BY completada ASC,
        CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 ELSE 3 END ASC
    """)
    fun obtenerPorParticipante(participanteId: Int): Flow<List<Tarea>>

    /**
     * Tareas creadas por el Líder que aún no tienen participante asignado.
     * Útil para la pantalla de asignación pendiente.
     */
    @Query("""
        SELECT * FROM tareas 
        WHERE creadaPor = :liderId AND asignadoA IS NULL
        ORDER BY CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 ELSE 3 END ASC
    """)
    fun obtenerSinAsignar(liderId: Int): Flow<List<Tarea>>

    /**
     * Asigna un participante a una tarea existente.
     * Solo el Líder puede llamar esto.
     */
    @Query("UPDATE tareas SET asignadoA = :participanteId WHERE id = :tareaId")
    suspend fun asignarParticipante(tareaId: Int, participanteId: Int)

    /**
     * Desasigna el participante de una tarea (vuelve a sin asignar).
     */
    @Query("UPDATE tareas SET asignadoA = NULL WHERE id = :tareaId")
    suspend fun desasignar(tareaId: Int)

    // ── Queries de categoría ──────────────────────────────────────────────────

    /**
     * Tareas de un Líder filtradas por categoría.
     */
    @Query("""
        SELECT * FROM tareas 
        WHERE creadaPor = :liderId AND categoria = :categoria
        ORDER BY completada ASC
    """)
    fun obtenerPorCategoriaYLider(liderId: Int, categoria: String): Flow<List<Tarea>>

    /**
     * Tareas de un Participante filtradas por categoría.
     */
    @Query("""
        SELECT * FROM tareas 
        WHERE asignadoA = :participanteId AND categoria = :categoria
        ORDER BY completada ASC
    """)
    fun obtenerPorCategoriaYParticipante(participanteId: Int, categoria: String): Flow<List<Tarea>>

    // ── Búsqueda ──────────────────────────────────────────────────────────────

    /**
     * Búsqueda por título para el Líder.
     */
    @Query("""
        SELECT * FROM tareas 
        WHERE creadaPor = :liderId AND titulo LIKE '%' || :texto || '%'
        ORDER BY completada ASC
    """)
    fun buscarPorLider(liderId: Int, texto: String): Flow<List<Tarea>>

    /**
     * Búsqueda por título para el Participante.
     */
    @Query("""
        SELECT * FROM tareas 
        WHERE asignadoA = :participanteId AND titulo LIKE '%' || :texto || '%'
        ORDER BY completada ASC
    """)
    fun buscarPorParticipante(participanteId: Int, texto: String): Flow<List<Tarea>>

    // ── Estadísticas ──────────────────────────────────────────────────────────

    @Query("SELECT COUNT(*) FROM tareas WHERE creadaPor = :liderId")
    suspend fun contarTotalPorLider(liderId: Int): Int

    @Query("SELECT COUNT(*) FROM tareas WHERE creadaPor = :liderId AND completada = 1")
    suspend fun contarCompletadasPorLider(liderId: Int): Int

    @Query("SELECT COUNT(*) FROM tareas WHERE asignadoA = :participanteId")
    suspend fun contarTotalPorParticipante(participanteId: Int): Int

    @Query("SELECT COUNT(*) FROM tareas WHERE asignadoA = :participanteId AND completada = 1")
    suspend fun contarCompletadasPorParticipante(participanteId: Int): Int

    @Query("SELECT COUNT(*) FROM tareas WHERE creadaPor = :liderId AND categoria = :categoria")
    suspend fun contarPorCategoriaYLider(liderId: Int, categoria: String): Int

    @Query("SELECT COUNT(*) FROM tareas WHERE creadaPor = :liderId AND asignadoA IS NULL")
    suspend fun contarSinAsignarPorLider(liderId: Int): Int

    // ── Compatibilidad (sin rol) ──────────────────────────────────────────────

    /**
     * Query general sin filtro de rol — se usa solo internamente.
     * No usar en Activities directamente.
     */
    @Query("""
        SELECT * FROM tareas 
        ORDER BY completada ASC,
        CASE prioridad WHEN 'Alta' THEN 1 WHEN 'Media' THEN 2 ELSE 3 END ASC
    """)
    fun obtenerTodas(): Flow<List<Tarea>>

    @Query("SELECT COUNT(*) FROM tareas")
    suspend fun contarTotal(): Int

    @Query("SELECT COUNT(*) FROM tareas WHERE completada = 1")
    suspend fun contarCompletadas(): Int

    @Query("SELECT COUNT(*) FROM tareas WHERE categoria = :categoria")
    suspend fun contarPorCategoria(categoria: String): Int
}
