package com.taskflow.app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.taskflow.app.R
import com.taskflow.app.adapter.TareaAdapter
import com.taskflow.app.data.db.TaskFlowDatabase
import com.taskflow.app.data.repository.TareaRepository
import com.taskflow.app.databinding.ActivityMainBinding
import com.taskflow.app.model.Tarea
import com.taskflow.app.util.SesionManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * ─────────────────────────────────────────────
 *  MainActivity.kt — Pantalla Principal
 *  Lógica de botones: Jonathan Quinto
 * ─────────────────────────────────────────────
 *
 *  Comportamiento según rol:
 *
 *  LÍDER:
 *   - Ve todas las tareas que creó
 *   - Puede agregar, editar, eliminar y asignar
 *   - El título del toolbar muestra "TaskFlow — Líder"
 *   - El FAB (+) está visible
 *
 *  PARTICIPANTE:
 *   - Ve solo las tareas asignadas a él
 *   - Puede marcar tareas como completadas
 *   - NO puede crear, editar ni eliminar tareas
 *   - El FAB (+) está oculto
 *   - Los botones Editar/Eliminar están ocultos en el adapter
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sesionManager: SesionManager
    private lateinit var tareaRepository: TareaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val db = TaskFlowDatabase.obtenerInstancia(this)
        sesionManager = SesionManager(this)
        tareaRepository = TareaRepository(db.tareaDao(), db.usuarioDao(), sesionManager)

        configurarToolbarPorRol()
        configurarRecyclerView()
        observarTareas()
        configurarBotones()
    }

    /**
     * Adapta el toolbar según el rol del usuario activo.
     * El Líder ve el FAB. El Participante no.
     */
    private fun configurarToolbarPorRol() {
        val nombre = sesionManager.getNombre()
        val rol = if (sesionManager.esLider()) "Líder" else "Participante"
        supportActionBar?.subtitle = "$nombre · $rol"

        // Solo el Líder puede crear tareas
        binding.fabAgregarTarea.visibility =
            if (sesionManager.esLider()) View.VISIBLE else View.GONE
    }

    private fun configurarRecyclerView() {
        binding.rvTareas.layoutManager = LinearLayoutManager(this)
    }

    /**
     * Observa el repositorio en tiempo real.
     * El repositorio decide automáticamente qué query usar según el rol.
     */
    private fun observarTareas() {
        lifecycleScope.launch {
            try {
                tareaRepository.obtenerTareasPorRol().collectLatest { listaTareas ->
                    actualizarUI(listaTareas)
                }
            } catch (e: Exception) {
                mostrarEstadoVacio(true)
            }
        }
    }

    private fun actualizarUI(lista: List<Tarea>) {
        if (lista.isEmpty()) {
            mostrarEstadoVacio(true)
            return
        }

        mostrarEstadoVacio(false)

        val esLider = sesionManager.esLider()

        val adaptador = TareaAdapter(
            lista = lista,
            mostrarAcciones = esLider, // El participante no ve Editar/Eliminar
            onEditar = { tarea ->
                if (esLider) {
                    val intent = Intent(this, EditarTareaActivity::class.java)
                    intent.putExtra("TAREA_ID", tarea.id)
                    startActivity(intent)
                }
            },
            onEliminar = { tarea ->
                if (esLider) confirmarEliminar(tarea)
            },
            onCompletarToggle = { tarea ->
                cambiarEstadoTarea(tarea)
            }
        )
        binding.rvTareas.adapter = adaptador
    }

    private fun mostrarEstadoVacio(vacio: Boolean) {
        binding.layoutVacio.visibility = if (vacio) View.VISIBLE else View.GONE
        binding.rvTareas.visibility = if (vacio) View.GONE else View.VISIBLE
    }

    /**
     * ── BOTONES ──
     * Responsable: Jonathan Quinto
     */
    private fun configurarBotones() {
        binding.fabAgregarTarea.setOnClickListener {
            startActivity(Intent(this, AgregarTareaActivity::class.java))
        }
    }

    private fun confirmarEliminar(tarea: Tarea) {
        android.app.AlertDialog.Builder(this)
            .setTitle("Eliminar tarea")
            .setMessage("¿Deseas eliminar \"${tarea.titulo}\"? Esta acción no se puede deshacer.")
            .setPositiveButton("Eliminar") { _, _ ->
                lifecycleScope.launch {
                    val ok = tareaRepository.eliminarTarea(tarea)
                    if (!ok) mostrarError("No se pudo eliminar la tarea.")
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun cambiarEstadoTarea(tarea: Tarea) {
        lifecycleScope.launch {
            val ok = tareaRepository.toggleCompletada(tarea)
            if (!ok) mostrarError("No se pudo actualizar la tarea.")
        }
    }

    private fun mostrarError(mensaje: String) {
        com.google.android.material.snackbar.Snackbar
            .make(binding.root, mensaje, com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_categorias -> {
                startActivity(Intent(this, CategoriasActivity::class.java))
                true
            }
            R.id.menu_estadisticas -> {
                startActivity(Intent(this, EstadisticasActivity::class.java))
                true
            }
            R.id.menu_perfil -> {
                startActivity(Intent(this, PerfilActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
