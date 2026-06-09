package com.taskflow.app.ui.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taskflow.app.data.db.TaskFlowDatabase
import com.taskflow.app.databinding.ActivityAgregarTareaBinding
import com.taskflow.app.model.Tarea
import com.taskflow.app.util.ValidadorTarea
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

/**
 * ─────────────────────────────────────────────
 *  AgregarTareaActivity.kt — Pantalla Agregar
 *  Lógica y validaciones: Jonathan Quinto
 * ─────────────────────────────────────────────
 *
 *  Flujo de esta pantalla:
 *  1. Usuario llena el formulario
 *  2. Presiona GUARDAR
 *  3. ValidadorTarea revisa que el título no esté vacío
 *  4. Si pasa la validación → se guarda en Room
 *  5. Si falla → se muestra error en el campo
 */
class AgregarTareaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarTareaBinding
    private val db by lazy { TaskFlowDatabase.obtenerInstancia(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarTareaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Nueva Tarea"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        configurarSpinners()
        configurarBotones()
    }

    /**
     * Carga las opciones de los Spinners de Prioridad y Categoría.
     */
    private fun configurarSpinners() {
        val prioridades = arrayOf("Alta", "Media", "Baja")
        val categorias = arrayOf("Personal", "Trabajo", "Estudios", "Compras")

        binding.spinnerPrioridad.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, prioridades
        )
        binding.spinnerPrioridad.setSelection(1) // Media por defecto

        binding.spinnerCategoria.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_dropdown_item, categorias
        )
    }

    /**
     * ── BOTÓN GUARDAR ──
     * Responsable: Jonathan Quinto
     *
     * Pasos:
     * 1. Lee el texto del campo título
     * 2. Llama a ValidadorTarea.validarFormulario()
     * 3. Si la validación falla → sale (el error ya se muestra en el campo)
     * 4. Si pasa → crea el objeto Tarea y lo inserta en la DB
     * 5. Usa try-catch para no crashear si la DB falla
     */
    private fun configurarBotones() {
        binding.btnGuardar.setOnClickListener {
            guardarTarea()
        }
    }

    private fun guardarTarea() {
        // Paso 1: Leer el título ingresado
        val titulo = binding.etTitulo.editText?.text.toString().trim()
        val descripcion = binding.etDescripcion.editText?.text.toString().trim()
        val prioridad = binding.spinnerPrioridad.selectedItem.toString()
        val categoria = binding.spinnerCategoria.selectedItem.toString()

        // Paso 2: Validar con ValidadorTarea
        val esValido = ValidadorTarea.validarFormulario(
            layoutTitulo = binding.etTitulo,
            titulo = titulo
        )

        // Paso 3: Si no es válido, salir (el error ya está en el campo)
        if (!esValido) return

        // Paso 4: Crear el objeto Tarea
        val fechaActual = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
        val nuevaTarea = Tarea(
            titulo = titulo,
            descripcion = descripcion,
            prioridad = prioridad,
            categoria = categoria,
            completada = false,
            fechaCreacion = fechaActual
        )

        // Paso 5: Guardar en la base de datos con try-catch
        lifecycleScope.launch {
            try {
                db.tareaDao().insertar(nuevaTarea)
                Toast.makeText(
                    this@AgregarTareaActivity,
                    "Tarea \"$titulo\" guardada",
                    Toast.LENGTH_SHORT
                ).show()
                finish() // Cierra esta pantalla y regresa al inicio
            } catch (e: Exception) {
                // Si la base de datos falla, mostramos error sin crashear
                Toast.makeText(
                    this@AgregarTareaActivity,
                    "Error al guardar la tarea. Intenta de nuevo.",
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
