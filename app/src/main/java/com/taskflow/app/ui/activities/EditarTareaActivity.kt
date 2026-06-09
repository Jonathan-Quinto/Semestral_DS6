package com.taskflow.app.ui.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taskflow.app.data.db.TaskFlowDatabase
import com.taskflow.app.databinding.ActivityEditarTareaBinding
import com.taskflow.app.model.Tarea
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
 *  2. Carga los datos actuales en los campos
 *  3. Usuario modifica lo que necesita
 *  4. Presiona ACTUALIZAR
 *  5. Se valida y guarda con try-catch
 */
class EditarTareaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditarTareaBinding
    private val db by lazy { TaskFlowDatabase.obtenerInstancia(this) }

    // Guardamos la tarea original para no perder el ID ni la fecha de creación
    private var tareaOriginal: Tarea? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarTareaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Editar Tarea"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        configurarSpinners()

        // Recibir el ID de la tarea que se quiere editar
        val tareaId = intent.getIntExtra("TAREA_ID", -1)
        if (tareaId == -1) {
            Toast.makeText(this, "Error: tarea no encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        cargarTarea(tareaId)
        configurarBotones()
    }

    private fun configurarSpinners() {
        val prioridades = arrayOf("Alta", "Media", "Baja")
        val categorias = arrayOf("Personal", "Trabajo", "Estudios", "Compras")

        binding.spinnerPrioridad.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, prioridades
        )
        binding.spinnerCategoria.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, categorias
        )
    }

    /**
     * Carga los datos de la tarea en los campos del formulario.
     * Usa try-catch por si la tarea fue eliminada entre pantallas.
     */
    private fun cargarTarea(id: Int) {
        lifecycleScope.launch {
            try {
                val tarea = db.tareaDao().obtenerPorId(id)
                if (tarea == null) {
                    Toast.makeText(
                        this@EditarTareaActivity,
                        "La tarea ya no existe",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                    return@launch
                }

                tareaOriginal = tarea

                // Llenar campos con datos actuales
                binding.etTitulo.editText?.setText(tarea.titulo)
                binding.etDescripcion.editText?.setText(tarea.descripcion)

                // Seleccionar la prioridad correcta en el spinner
                val prioridades = listOf("Alta", "Media", "Baja")
                binding.spinnerPrioridad.setSelection(prioridades.indexOf(tarea.prioridad))

                // Seleccionar la categoría correcta
                val categorias = listOf("Personal", "Trabajo", "Estudios", "Compras")
                binding.spinnerCategoria.setSelection(categorias.indexOf(tarea.categoria))

                // Mostrar estado de completada
                binding.checkboxCompletada.isChecked = tarea.completada

            } catch (e: Exception) {
                Toast.makeText(
                    this@EditarTareaActivity,
                    "Error al cargar la tarea",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    /**
     * ── BOTÓN ACTUALIZAR ──
     * Responsable: Jonathan Quinto
     *
     * Misma lógica que guardar pero con .actualizar() en lugar de .insertar()
     * Conserva el ID original y la fecha de creación.
     */
    private fun configurarBotones() {
        binding.btnActualizar.setOnClickListener {
            actualizarTarea()
        }
    }

    private fun actualizarTarea() {
        val titulo = binding.etTitulo.editText?.text.toString().trim()
        val descripcion = binding.etDescripcion.editText?.text.toString().trim()
        val prioridad = binding.spinnerPrioridad.selectedItem.toString()
        val categoria = binding.spinnerCategoria.selectedItem.toString()
        val completada = binding.checkboxCompletada.isChecked

        // Validar con ValidadorTarea
        val esValido = ValidadorTarea.validarFormulario(
            layoutTitulo = binding.etTitulo,
            titulo = titulo
        )
        if (!esValido) return

        val original = tareaOriginal ?: return

        // Crear copia actualizada conservando el ID y fecha original
        val tareaActualizada = original.copy(
            titulo = titulo,
            descripcion = descripcion,
            prioridad = prioridad,
            categoria = categoria,
            completada = completada
        )

        lifecycleScope.launch {
            try {
                db.tareaDao().actualizar(tareaActualizada)
                Toast.makeText(
                    this@EditarTareaActivity,
                    "Tarea actualizada",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(
                    this@EditarTareaActivity,
                    "Error al actualizar. Intenta de nuevo.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
