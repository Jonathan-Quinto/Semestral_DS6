package com.taskflow.app.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taskflow.app.data.db.TaskFlowDatabase
import com.taskflow.app.data.repository.TareaRepository
import com.taskflow.app.databinding.ActivityAgregarTareaBinding
import com.taskflow.app.model.Usuario
import com.taskflow.app.util.SesionManager
import com.taskflow.app.util.ValidadorTarea
import kotlinx.coroutines.launch

/**
 * ─────────────────────────────────────────────
 *  AgregarTareaActivity.kt — Pantalla Agregar
 *  Lógica y validaciones: Jonathan Quinto
 * ─────────────────────────────────────────────
 *
 *  Solo accesible para el Líder (el FAB está oculto
 *  para los Participantes en MainActivity).
 *
 *  Flujo:
 *  1. Líder llena título, descripción, prioridad, categoría
 *  2. Selecciona a cuál participante asignar (spinner)
 *     - Si no hay participantes → puede guardar sin asignar
 *  3. Presiona GUARDAR
 *  4. TareaRepository crea la tarea con creadaPor automático
 */
class AgregarTareaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarTareaBinding
    private lateinit var tareaRepository: TareaRepository

    // Lista de participantes disponibles para asignar
    private var participantes: List<Usuario> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarTareaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Nueva Tarea"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val db = TaskFlowDatabase.obtenerInstancia(this)
        val sesion = SesionManager(this)
        tareaRepository = TareaRepository(db.tareaDao(), db.usuarioDao(), sesion)

        configurarSpinners()
        cargarParticipantes()
        configurarBotones()
    }

    private fun configurarSpinners() {
        val prioridades = arrayOf("Alta", "Media", "Baja")
        val categorias  = arrayOf("Personal", "Trabajo", "Estudios", "Compras")

        binding.spinnerPrioridad.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, prioridades
        )
        binding.spinnerPrioridad.setSelection(1) // Media por defecto

        binding.spinnerCategoria.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, categorias
        )
    }

    /**
     * Carga los participantes disponibles para el spinner de asignación.
     * Si no hay ninguno, oculta el spinner y muestra un mensaje.
     */
    private fun cargarParticipantes() {
        lifecycleScope.launch {
            try {
                participantes = tareaRepository.obtenerParticipantes()

                if (participantes.isEmpty()) {
                    binding.layoutAsignar.visibility = View.GONE
                    binding.tvSinParticipantes.visibility = View.VISIBLE
                } else {
                    binding.layoutAsignar.visibility = View.VISIBLE
                    binding.tvSinParticipantes.visibility = View.GONE

                    // "Sin asignar" como primera opción
                    val opciones = listOf("Sin asignar") + participantes.map { it.nombre }
                    binding.spinnerAsignar.adapter = ArrayAdapter(
                        this@AgregarTareaActivity,
                        android.R.layout.simple_spinner_dropdown_item,
                        opciones
                    )
                }
            } catch (e: Exception) {
                binding.layoutAsignar.visibility = View.GONE
            }
        }
    }

    /**
     * ── BOTÓN GUARDAR ──
     * Responsable: Jonathan Quinto
     */
    private fun configurarBotones() {
        binding.btnGuardar.setOnClickListener {
            guardarTarea()
        }
    }

    private fun guardarTarea() {
        val titulo      = binding.etTitulo.editText?.text.toString().trim()
        val descripcion = binding.etDescripcion.editText?.text.toString().trim()
        val prioridad   = binding.spinnerPrioridad.selectedItem.toString()
        val categoria   = binding.spinnerCategoria.selectedItem.toString()
        val fechaLimite = binding.etFechaLimite.editText?.text.toString().trim()

        // Validar con ValidadorTarea
        val esValido = ValidadorTarea.validarFormulario(
            layoutTitulo = binding.etTitulo,
            titulo = titulo
        )
        if (!esValido) return

        // Determinar a quién asignar
        val posicionAsignado = binding.spinnerAsignar.selectedItemPosition
        val asignadoA: Int? = when {
            participantes.isEmpty() -> null          // No hay participantes
            posicionAsignado == 0   -> null          // "Sin asignar" seleccionado
            else -> participantes[posicionAsignado - 1].id
        }

        lifecycleScope.launch {
            try {
                val ok = tareaRepository.crearTarea(
                    titulo      = titulo,
                    descripcion = descripcion,
                    prioridad   = prioridad,
                    categoria   = categoria,
                    fechaLimite = fechaLimite,
                    asignadoA   = asignadoA
                )

                if (ok) {
                    Toast.makeText(
                        this@AgregarTareaActivity,
                        "Tarea \"$titulo\" guardada",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                } else {
                    Toast.makeText(
                        this@AgregarTareaActivity,
                        "Error al guardar la tarea. Intenta de nuevo.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@AgregarTareaActivity,
                    "Error inesperado. Intenta de nuevo.",
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
