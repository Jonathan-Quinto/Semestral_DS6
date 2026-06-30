package com.taskflow.app.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taskflow.app.data.db.TaskFlowDatabase
import com.taskflow.app.data.repository.TareaRepository
import com.taskflow.app.databinding.ActivityEstadisticasBinding
import com.taskflow.app.util.SesionManager
import kotlinx.coroutines.launch

/**
 * ─────────────────────────────────────────────
 *  EstadisticasActivity.kt
 * ─────────────────────────────────────────────
 *
 *  Muestra estadísticas según el rol:
 *
 *  LÍDER:
 *   - Total de tareas creadas
 *   - Completadas / Pendientes
 *   - Sin asignar
 *   - Porcentaje de progreso del equipo
 *   - Conteo por categoría (solo las que él creó)
 *
 *  PARTICIPANTE:
 *   - Total de tareas asignadas a él
 *   - Completadas / Pendientes
 *   - Porcentaje de progreso personal
 *   - Sección "Sin asignar" oculta
 *   - Conteo por categoría (solo las que le asignaron)
 */
class EstadisticasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEstadisticasBinding
    private lateinit var tareaRepository: TareaRepository
    private lateinit var sesionManager: SesionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstadisticasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Estadísticas"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = TaskFlowDatabase.obtenerInstancia(this)
        sesionManager = SesionManager(this)
        tareaRepository = TareaRepository(db.tareaDao(), db.usuarioDao(), sesionManager)

        cargarEstadisticas()

        binding.btnVolver.setOnClickListener { finish() }
    }

    private fun cargarEstadisticas() {
        lifecycleScope.launch {
            try {
                val total       = tareaRepository.contarTotal()
                val completadas = tareaRepository.contarCompletadas()
                val pendientes  = total - completadas
                val porcentaje  = if (total > 0) (completadas * 100) / total else 0

                binding.tvTotalTareas.text       = total.toString()
                binding.tvTareasCompletadas.text = completadas.toString()
                binding.tvTareasPendientes.text  = pendientes.toString()
                binding.tvPorcentaje.text        = "$porcentaje%"
                binding.progressGeneral.max      = if (total > 0) total else 1
                binding.progressGeneral.progress = completadas

                // Mostrar "Sin asignar" solo al Líder
                if (sesionManager.esLider()) {
                    val sinAsignar = tareaRepository.contarSinAsignar()
                    binding.layoutSinAsignar.visibility = android.view.View.VISIBLE
                    binding.tvSinAsignar.text = "Sin asignar: $sinAsignar"
                } else {
                    binding.layoutSinAsignar.visibility = android.view.View.GONE
                }

                // Conteo por categoría — filtrado correctamente por rol
                val dao       = TaskFlowDatabase.obtenerInstancia(this@EstadisticasActivity).tareaDao()
                val usuarioId = sesionManager.getUsuarioId()

                val categorias = listOf("Personal", "Trabajo", "Estudios", "Compras")
                val conteos = if (sesionManager.esLider()) {
                    categorias.map { dao.contarPorCategoriaYLider(usuarioId, it) }
                } else {
                    // FIX: antes usaba contarPorCategoria (global), ahora filtra
                    // solo las tareas asignadas al participante activo
                    categorias.map { dao.contarPorCategoriaYParticipante(usuarioId, it) }
                }

                binding.tvPersonal.text = "Personal: ${conteos[0]} tareas"
                binding.tvTrabajo.text  = "Trabajo: ${conteos[1]} tareas"
                binding.tvEstudios.text = "Estudios: ${conteos[2]} tareas"
                binding.tvCompras.text  = "Compras: ${conteos[3]} tareas"

            } catch (e: Exception) {
                binding.tvTotalTareas.text       = "0"
                binding.tvTareasCompletadas.text = "0"
                binding.tvTareasPendientes.text  = "0"
                binding.tvPorcentaje.text        = "0%"

                com.google.android.material.snackbar.Snackbar
                    .make(binding.root, "Error al cargar estadísticas", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
