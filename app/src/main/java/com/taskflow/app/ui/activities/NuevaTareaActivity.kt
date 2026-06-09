package com.taskflow.app.ui.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taskflow.app.data.db.TaskFlowDatabase
import com.taskflow.app.databinding.ActivityNuevaTareaBinding
import com.taskflow.app.model.Tarea
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NuevaTareaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNuevaTareaBinding
    private val db by lazy { TaskFlowDatabase.getInstance(this) }
    private var tareaExistente: Tarea? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevaTareaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val prioridades = listOf("Alta", "Media", "Baja")
        binding.spinnerPrioridad.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, prioridades)

        cargarCategorias()

        val tareaId = intent.getIntExtra("TAREA_ID", -1)
        if (tareaId != -1) {
            supportActionBar?.title = "Cambiar Tarea"
            binding.btnGuardar.text = "ACTUALIZAR"
            cargarTareaExistente(tareaId)
        } else {
            supportActionBar?.title = "Agregar Tarea"
            binding.btnGuardar.text = "GUARDAR"
        }

        binding.btnGuardar.setOnClickListener { guardarTarea() }
    }

    private fun cargarCategorias() {
        lifecycleScope.launch {
            val categorias = db.categoriaDao().obtenerTodas().first().map { it.nombre }
            val adapter = ArrayAdapter(this@NuevaTareaActivity,
                android.R.layout.simple_spinner_dropdown_item, categorias)
            binding.spinnerCategoria.adapter = adapter
        }
    }

    private fun cargarTareaExistente(id: Int) {
        lifecycleScope.launch {
            val tarea = db.tareaDao().obtenerPorId(id) ?: return@launch
            tareaExistente = tarea
            binding.etTitulo.setText(tarea.titulo)
            binding.etDescripcion.setText(tarea.descripcion)

            val prioridades = listOf("Alta", "Media", "Baja")
            binding.spinnerPrioridad.setSelection(prioridades.indexOf(tarea.prioridad))
        }
    }

    private fun guardarTarea() {
        val titulo = binding.etTitulo.text.toString().trim()
        if (titulo.isEmpty()) {
            binding.etTitulo.error = "El título es obligatorio"
            return
        }

        val descripcion = binding.etDescripcion.text.toString().trim()
        val prioridad = binding.spinnerPrioridad.selectedItem.toString()
        val categoria = binding.spinnerCategoria.selectedItem?.toString() ?: "Personal"

        lifecycleScope.launch {
            if (tareaExistente != null) {
                db.tareaDao().actualizar(
                    tareaExistente!!.copy(
                        titulo = titulo,
                        descripcion = descripcion,
                        prioridad = prioridad,
                        categoria = categoria
                    )
                )
                Toast.makeText(this@NuevaTareaActivity, "Tarea actualizada", Toast.LENGTH_SHORT).show()
            } else {
                db.tareaDao().insertar(
                    Tarea(
                        titulo = titulo,
                        descripcion = descripcion,
                        prioridad = prioridad,
                        categoria = categoria
                    )
                )
                Toast.makeText(this@NuevaTareaActivity, "Tarea guardada", Toast.LENGTH_SHORT).show()
            }
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
