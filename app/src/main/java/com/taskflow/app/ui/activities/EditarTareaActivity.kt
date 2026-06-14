package com.taskflow.app.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taskflow.app.data.db.TaskFlowDatabase
import com.taskflow.app.data.repository.TareaRepository
import com.taskflow.app.databinding.ActivityEditarTareaBinding
import com.taskflow.app.model.Tarea
import com.taskflow.app.model.Usuario
import com.taskflow.app.util.SesionManager
import com.taskflow.app.util.ValidadorTarea
import kotlinx.coroutines.launch

/**
 * ─────────────────────────────────────────────
 *  EditarTareaActivity.kt — Pantalla Editar
 *  Lógica y validaciones: Jonathan Quinto
 * ─────────────────────────────────────────────
 *
 *  Flujo:
 *  1. Recibe el ID de la tarea desde MainActivity
 *  2. Carga los datos actuales (título, desc, prioridad, categoría,
 *     fecha límite y asignación actual)
 *  3. El Líder puede reasignar la tarea a otro participante
 *  4. Presiona ACTUALIZAR → valida y guarda con TareaRepository
 */
class EditarTareaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarTareaBinding
    private lateinit var tareaRepository: TareaRepository

    private var tareaOriginal: Tarea? = null
    private var participantes: List<Usuario> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarTareaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Editar Tarea"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = TaskFlowDatabase.obtenerInstancia(this)
        val sesion = SesionManager(this)
        tareaRepository = TareaRepository(db.tareaDao(), db.usuarioDao(), sesion)

        configurarSpinners()

        val tareaId = intent.getIntExtra("TAREA_ID", -1)
        if (tareaId == -1) {
            Toast.makeText(this, "Error: tarea no encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        cargarTareaYParticipantes(tareaId)
        configurarBotones()
    }

    private fun configurarSpinners() {
        val prioridades = arrayOf("Alta", "Media", "Baja")
        val categorias  = arrayOf("Personal", "Trabajo", "Estudios", "Compras")

        binding.spinnerPrioridad.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, prioridades
        )
        binding.spinnerCategoria.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, categorias
        )
    }

    /**
     * Carga la tarea y los participantes disponibles en paralelo.
     */
    private fun cargarTareaYParticipantes(id: Int) {
        lifecycleScope.launch {
            try {
                // Cargar tarea
                val tarea = tareaRepository.obtenerPorId(id)
                if (tarea == null) {
                    Toast.makeText(this@EditarTareaActivity, "La tarea ya no existe", Toast.LENGTH_SHORT).show()
                    finish()
                    return@launch
                }
                tareaOriginal = tarea

                // Llenar campos
                binding.etTitulo.editText?.setText(tarea.titulo)
                binding.etDescripcion.editText?.setText(tarea.descripcion)
                binding.etFechaLimite.editText?.setText(tarea.fechaLimite)

                val prioridades = listOf("Alta", "Media", "Baja")
                binding.spinnerPrioridad.setSelection(
                    prioridades.indexOf(tarea.prioridad).coerceAtLeast(0)
                )
                val categorias = listOf("Personal", "Trabajo", "Estudios", "Compras")
                binding.spinnerCategoria.setSelection(
                    categorias.indexOf(tarea.categoria).coerceAtLeast(0)
                )
                binding.checkboxCompletada.isChecked = tarea.completada

                // Cargar participantes para el spinner de reasignación
                participantes = tareaRepository.obtenerParticipantes()

                if (participantes.isEmpty()) {
                    binding.layoutAsignar.visibility = View.GONE
                } else {
                    binding.layoutAsignar.visibility = View.VISIBLE
                    val opciones = listOf("Sin asignar") + participantes.map { it.nombre }
                    binding.spinnerAsignar.adapter = ArrayAdapter(
                        this@EditarTareaActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        opciones
                    )

                    // Preseleccionar el participante actual
                    val idAsignado = tarea.asignadoA
                    if (idAsignado != null) {
                        val idx = participantes.indexOfFirst { it.id == idAsignado }
                        if (idx >= 0) binding.spinnerAsignar.setSelection(idx + 1)
                    }
                }

            } catch (e: Exception) {
                Toast.makeText(this@EditarTareaActivity, "Error al cargar la tarea", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    /**
     * ── BOTÓN ACTUALIZAR ──
     * Responsable: Jonathan Quinto
     */
    private fun configurarBotones() {
        binding.btnActualizar.setOnClickListener {
            actualizarTarea()
        }
    }

    private fun actualizarTarea() {
        val titulo      = binding.etTitulo.editText?.text.toString().trim()
        val descripcion = binding.etDescripcion.editText?.text.toString().trim()
        val prioridad   = binding.spinnerPrioridad.selectedItem.toString()
        val categoria   = binding.spinnerCategoria.selectedItem.toString()
        val completada  = binding.checkboxCompletada.isChecked
        val fechaLimite = binding.etFechaLimite.editText?.text.toString().trim()

        val esValido = ValidadorTarea.validarFormulario(
            layoutTitulo = binding.etTitulo,
            titulo = titulo
        )
        if (!esValido) return

        val original = tareaOriginal ?: return

        // Determinar nueva asignación
        val posicion = binding.spinnerAsignar.selectedItemPosition
        val asignadoA: Int? = when {
            participantes.isEmpty() -> original.asignadoA
            posicion == 0           -> null
            else                    -> participantes[posicion - 1].id
        }

        val tareaActualizada = original.copy(
            titulo      = titulo,
            descripcion = descripcion,
            prioridad   = prioridad,
            categoria   = categoria,
            completada  = completada,
            fechaLimite = fechaLimite,
            asignadoA   = asignadoA
        )

        lifecycleScope.launch {
            try {
                val ok = tareaRepository.actualizarTarea(tareaActualizada)
                if (ok) {
                    Toast.makeText(this@EditarTareaActivity, "Tarea actualizada", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@EditarTareaActivity, "Error al actualizar. Intenta de nuevo.", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@EditarTareaActivity, "Error inesperado.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
