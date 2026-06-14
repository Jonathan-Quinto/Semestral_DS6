package com.taskflow.app.data.db

import androidx.room.*
import com.taskflow.app.model.Usuario
import kotlinx.coroutines.flow.Flow

/**
 * DAO de usuarios — operaciones de base de datos para el sistema de autenticación y roles.
 */
@Dao
interface UsuarioDao {

    // ── Registro ──────────────────────────────────────────────────────────────

    /**
     * Inserta un nuevo usuario.
     * Devuelve el ID generado, o -1 si el email ya existe (IGNORE).
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(usuario: Usuario): Long

    // ── Autenticación ─────────────────────────────────────────────────────────

    /**
     * Busca un usuario por email y contraseña hasheada.
     * Usado en el login — si devuelve null, las credenciales son incorrectas.
     */
    @Query("SELECT * FROM usuarios WHERE email = :email AND passwordHash = :passwordHash LIMIT 1")
    suspend fun login(email: String, passwordHash: String): Usuario?

    /**
     * Verifica si un email ya está registrado.
     * Usado en el registro para mostrar error antes de intentar insertar.
     */
    @Query("SELECT COUNT(*) FROM usuarios WHERE email = :email")
    suspend fun existeEmail(email: String): Int

    // ── Consultas por rol ─────────────────────────────────────────────────────

    /**
     * Obtiene todos los participantes disponibles para asignar tareas.
     * Solo el Líder necesita esta lista (para el spinner de asignación).
     */
    @Query("SELECT * FROM usuarios WHERE rol = 'PARTICIPANTE' ORDER BY nombre ASC")
    fun obtenerParticipantes(): Flow<List<Usuario>>

    /**
     * Versión suspend de obtenerParticipantes — para cargar una sola vez (spinner).
     */
    @Query("SELECT * FROM usuarios WHERE rol = 'PARTICIPANTE' ORDER BY nombre ASC")
    suspend fun obtenerParticipantesSuspend(): List<Usuario>

    /**
     * Obtiene todos los usuarios (para pantalla de administración del Líder).
     */
    @Query("SELECT * FROM usuarios ORDER BY rol ASC, nombre ASC")
    fun obtenerTodos(): Flow<List<Usuario>>

    // ── Perfil ────────────────────────────────────────────────────────────────

    /**
     * Obtiene un usuario por su ID — para mostrar el perfil activo.
     */
    @Query("SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    suspend fun obtenerPorId(id: Int): Usuario?

    /**
     * Actualiza los datos de perfil de un usuario (nombre).
     * El email y rol no se cambian desde aquí por seguridad.
     */
    @Query("UPDATE usuarios SET nombre = :nombre WHERE id = :id")
    suspend fun actualizarNombre(id: Int, nombre: String)
}
