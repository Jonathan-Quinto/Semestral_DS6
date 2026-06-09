package com.taskflow.app.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taskflow.app.data.db.TaskFlowDatabase
import com.taskflow.app.databinding.ActivityEstadisticasBinding
import kotlinx.coroutines.launch

/**
 * ─────────────────────────────────────────────
 *  EstadisticasActivity.kt — Pantalla Estadísticas
 * ─────────────────────────────────────────────
 *
 *  Muestra:
 *  - Total de tareas
 *  - Tareas completadas
 *  - Porcentaje de progreso
 *  - Conteo por categoría
 */
class EstadisticasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEstadisticasBinding
    private val db by lazy { TaskFlowDatabase.obtenerInstancia(this) }

    private val categorias = listOf("Personal", "Trabajo", "Estudios", "Compras")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEstadisticasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Estadísticas"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        cargarEstadisticas()

        binding.btnVolver.setOnClickListener {
            finish()
        }
    }

    /**
     * Carga todas las estadísticas de la base de datos.
     * Usa try-catch para no crashear si algo falla.
     */
    private fun cargarEstadisticas() {
        lifecycleScope.launch {
            try {
                val total = db.tareaDao().contarTotal()
                val completadas = db.tareaDao().contarCompletadas()
                val pendientes = total - completadas

                // Calcular porcentaje de progreso
                val porcentaje = if (total > 0) {
                    (completadas * 100) / total
                } else {
                    0
                }

                // Actualizar UI
                binding.tvTotalTareas.text = total.toString()
                binding.tvTareasCompletadas.text = completadas.toString()
                binding.tvTareasPendientes.text = pendientes.toString()
                binding.tvPorcentaje.text = "$porcentaje%"
                binding.progressGeneral.max = if (total > 0) total else 1
                binding.progressGeneral.progress = completadas

                // Conteo por categoría
                binding.tvPersonal.text = "Personal: ${db.tareaDao().contarPorCategoria("Personal")} tareas"
                binding.tvTrabajo.text = "Trabajo: ${db.tareaDao().contarPorCategoria("Trabajo")} tareas"
                binding.tvEstudios.text = "Estudios: ${db.tareaDao().contarPorCategoria("Estudios")} tareas"
                binding.tvCompras.text = "Compras: ${db.tareaDao().contarPorCategoria("Compras")} tareas"

            } catch (e: Exception) {
                // Si falla, mostramos ceros en lugar de crashear
                binding.tvTotalTareas.text = "0"
                binding.tvTareasCompletadas.text = "0"
                binding.tvTareasPendientes.text = "0"
                binding.tvPorcentaje.text = "0%"

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
