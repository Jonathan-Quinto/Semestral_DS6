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
 *   - Sin asignar (extra útil para el líder)
 *   - Porcentaje de progreso del equipo
 *
 *  PARTICIPANTE:
 *   - Total de tareas asignadas a él
 *   - Completadas / Pendientes
 *   - Porcentaje de progreso personal
 *   - La sección "Sin asignar" queda oculta
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
                val total      = tareaRepository.contarTotal()
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

                // Conteo por categoría (usando DAO directo porque el repo no tiene este método por rol aún)
                val dao = TaskFlowDatabase.obtenerInstancia(this@EstadisticasActivity).tareaDao()
                val usuarioId = sesionManager.getUsuarioId()

                if (sesionManager.esLider()) {
                    binding.tvPersonal.text  = "Personal: ${dao.contarPorCategoriaYLider(usuarioId, "Personal")} tareas"
                    binding.tvTrabajo.text   = "Trabajo: ${dao.contarPorCategoriaYLider(usuarioId, "Trabajo")} tareas"
                    binding.tvEstudios.text  = "Estudios: ${dao.contarPorCategoriaYLider(usuarioId, "Estudios")} tareas"
                    binding.tvCompras.text   = "Compras: ${dao.contarPorCategoriaYLider(usuarioId, "Compras")} tareas"
                } else {
                    binding.tvPersonal.text  = "Personal: ${dao.contarPorCategoria("Personal")} tareas"
                    binding.tvTrabajo.text   = "Trabajo: ${dao.contarPorCategoria("Trabajo")} tareas"
                    binding.tvEstudios.text  = "Estudios: ${dao.contarPorCategoria("Estudios")} tareas"
                    binding.tvCompras.text   = "Compras: ${dao.contarPorCategoria("Compras")} tareas"
                }

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
