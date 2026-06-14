package com.taskflow.app.data.repository

import com.taskflow.app.data.db.TareaDao
import com.taskflow.app.data.db.UsuarioDao
import com.taskflow.app.model.Tarea
import com.taskflow.app.model.Usuario
import com.taskflow.app.util.SesionManager
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.*

/**
 * ─────────────────────────────────────────────
 *  TareaRepository.kt
 *  Responsable: Jonathan Quinto
 *  Propósito: Centralizar la lógica de negocio
 *  de tareas con conciencia del rol activo.
 * ─────────────────────────────────────────────
 *
 *  El repositorio recibe el SesionManager y decide
 *  automáticamente qué query usar según el rol:
 *
 *   Líder       → ve todas las tareas que creó
 *   Participante → ve solo las que le asignaron
 *
 *  Las Activities solo llaman a este repositorio,
 *  sin preocuparse por cuál query usar.
 */
class TareaRepository(
    private val tareaDao: TareaDao,
    private val usuarioDao: UsuarioDao,
    private val sesionManager: SesionManager
) {

    // ── Lectura con rol automático ────────────────────────────────────────────

    /**
     * Devuelve el Flow de tareas según el rol del usuario activo.
     * La Activity observa esto sin saber si es Líder o Participante.
     */
    fun obtenerTareasPorRol(): Flow<List<Tarea>> {
        val id = sesionManager.getUsuarioId()
        return if (sesionManager.esLider()) {
            tareaDao.obtenerPorLider(id)
        } else {
            tareaDao.obtenerPorParticipante(id)
        }
    }

    /**
     * Tareas sin asignar del Líder activo.
     * Solo tiene sentido llamarlo cuando esLider() == true.
     */
    fun obtenerSinAsignar(): Flow<List<Tarea>> {
        return tareaDao.obtenerSinAsignar(sesionManager.getUsuarioId())
    }

    /**
     * Búsqueda por texto con conciencia de rol.
     */
    fun buscar(texto: String): Flow<List<Tarea>> {
        val id = sesionManager.getUsuarioId()
        return if (sesionManager.esLider()) {
            tareaDao.buscarPorLider(id, texto)
        } else {
            tareaDao.buscarPorParticipante(id, texto)
        }
    }

    /**
     * Tareas filtradas por categoría con conciencia de rol.
     */
    fun obtenerPorCategoria(categoria: String): Flow<List<Tarea>> {
        val id = sesionManager.getUsuarioId()
        return if (sesionManager.esLider()) {
            tareaDao.obtenerPorCategoriaYLider(id, categoria)
        } else {
            tareaDao.obtenerPorCategoriaYParticipante(id, categoria)
        }
    }

    // ── Escritura ─────────────────────────────────────────────────────────────

    /**
     * Crea una nueva tarea asignando automáticamente el Líder activo como creador.
     * Solo el Líder puede crear tareas.
     *
     * @return true si se insertó correctamente
     */
    suspend fun crearTarea(
        titulo: String,
        descripcion: String,
        prioridad: String,
        categoria: String,
        fechaLimite: String = "",
        asignadoA: Int? = null
    ): Boolean {
        return try {
            val fechaActual = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
            val nuevaTarea = Tarea(
                titulo = titulo,
                descripcion = descripcion,
                prioridad = prioridad,
                categoria = categoria,
                completada = false,
                fechaCreacion = fechaActual,
                fechaLimite = fechaLimite,
                creadaPor = sesionManager.getUsuarioId(),
                asignadoA = asignadoA
            )
            tareaDao.insertar(nuevaTarea)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun actualizarTarea(tarea: Tarea): Boolean {
        return try {
            tareaDao.actualizar(tarea)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun eliminarTarea(tarea: Tarea): Boolean {
        return try {
            tareaDao.eliminar(tarea)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Cambia el estado completada/pendiente de una tarea.
     * El Participante puede hacerlo en sus propias tareas.
     * El Líder puede hacerlo en cualquier tarea que creó.
     */
    suspend fun toggleCompletada(tarea: Tarea): Boolean {
        return try {
            tareaDao.actualizar(tarea.copy(completada = !tarea.completada))
            true
        } catch (e: Exception) {
            false
        }
    }

    // ── Asignación (solo Líder) ───────────────────────────────────────────────

    /**
     * Asigna una tarea a un participante.
     * Verifica internamente que el usuario activo sea Líder.
     *
     * @return true si se asignó, false si no es Líder o falló la BD
     */
    suspend fun asignarTarea(tareaId: Int, participanteId: Int): Boolean {
        if (!sesionManager.esLider()) return false
        return try {
            tareaDao.asignarParticipante(tareaId, participanteId)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Quita la asignación de una tarea (vuelve a "sin asignar").
     */
    suspend fun desasignarTarea(tareaId: Int): Boolean {
        if (!sesionManager.esLider()) return false
        return try {
            tareaDao.desasignar(tareaId)
            true
        } catch (e: Exception) {
            false
        }
    }

    // ── Lista de participantes (para el spinner del Líder) ────────────────────

    /**
     * Devuelve todos los participantes disponibles para asignar.
     * El Líder usa esta lista en el formulario de creación/edición.
     */
    suspend fun obtenerParticipantes(): List<Usuario> {
        return try {
            usuarioDao.obtenerParticipantesSuspend()
        } catch (e: Exception) {
            emptyList()
        }
    }

    // ── Estadísticas con rol ──────────────────────────────────────────────────

    /**
     * Conteo de tareas totales según el rol activo.
     */
    suspend fun contarTotal(): Int {
        val id = sesionManager.getUsuarioId()
        return if (sesionManager.esLider()) {
            tareaDao.contarTotalPorLider(id)
        } else {
            tareaDao.contarTotalPorParticipante(id)
        }
    }

    /**
     * Conteo de tareas completadas según el rol activo.
     */
    suspend fun contarCompletadas(): Int {
        val id = sesionManager.getUsuarioId()
        return if (sesionManager.esLider()) {
            tareaDao.contarCompletadasPorLider(id)
        } else {
            tareaDao.contarCompletadasPorParticipante(id)
        }
    }

    /**
     * Tareas sin asignar del Líder activo (solo útil para el Líder).
     */
    suspend fun contarSinAsignar(): Int {
        return if (sesionManager.esLider()) {
            tareaDao.contarSinAsignarPorLider(sesionManager.getUsuarioId())
        } else {
            0
        }
    }

    suspend fun obtenerPorId(id: Int): Tarea? = tareaDao.obtenerPorId(id)
}
