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
import com.taskflow.app.model.Usuario
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
 *   - El FAB (+) está visible
 *   - Cada tarea muestra a quién está asignada
 *   - Estado vacío: "Presiona + para agregar una tarea"
 *
 *  PARTICIPANTE:
 *   - Ve solo las tareas asignadas a él
 *   - Puede marcar tareas como completadas
 *   - NO puede crear, editar ni eliminar tareas
 *   - El FAB (+) está oculto
 *   - Estado vacío: "El líder aún no te ha asignado tareas"
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sesionManager: SesionManager
    private lateinit var tareaRepository: TareaRepository

    // Mapa de id → nombre de participantes (solo se carga si el usuario es Líder)
    private var nombresParticipantes: Map<Int, String> = emptyMap()

    // Categoría activa para filtrar (viene de CategoriasActivity). Null = sin filtro.
    private var filtroCategoria: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        filtroCategoria = intent.getStringExtra("FILTRO_CATEGORIA")

        val db = TaskFlowDatabase.obtenerInstancia(this)
        sesionManager = SesionManager(this)
        tareaRepository = TareaRepository(db.tareaDao(), db.usuarioDao(), sesionManager)

        configurarToolbarPorRol()
        configurarRecyclerView()

        // El Líder necesita los nombres de participantes para mostrarlos en las tarjetas
        if (sesionManager.esLider()) {
            cargarNombresParticipantes()
        } else {
            observarTareas()
        }

        configurarBotones()
    }

    private fun configurarToolbarPorRol() {
        val nombre = sesionManager.getNombre()
        val rol = if (sesionManager.esLider()) "Líder" else "Participante"

        supportActionBar?.subtitle = if (filtroCategoria != null) {
            "$nombre · $rol · Categoría: ${filtroCategoria} (toca el título para quitar)"
        } else {
            "$nombre · $rol"
        }

        // Tocar el título de la toolbar quita el filtro de categoría activo
        binding.toolbar.setOnClickListener {
            if (filtroCategoria != null) {
                filtroCategoria = null
                configurarToolbarPorRol()
                observarTareas()
            }
        }

        binding.fabAgregarTarea.visibility =
            if (sesionManager.esLider()) View.VISIBLE else View.GONE

        binding.tvVacioSubtitulo.text = when {
            filtroCategoria != null -> "No hay tareas en esta categoría"
            sesionManager.esLider() -> "Presiona + para agregar una tarea"
            else -> "El líder aún no te ha asignado tareas"
        }
    }

    /**
     * Carga el mapa id→nombre de participantes antes de observar las tareas,
     * para que el adapter pueda mostrar "Asignado a: [nombre]" en cada tarjeta.
     */
    private fun cargarNombresParticipantes() {
        lifecycleScope.launch {
            try {
                val participantes: List<Usuario> = tareaRepository.obtenerParticipantes()
                nombresParticipantes = participantes.associate { it.id to it.nombre }
            } catch (_: Exception) { }
            observarTareas()
        }
    }

    private fun configurarRecyclerView() {
        binding.rvTareas.layoutManager = LinearLayoutManager(this)
    }

    private fun observarTareas() {
        lifecycleScope.launch {
            try {
                val flow = filtroCategoria?.let { categoria ->
                    tareaRepository.obtenerPorCategoria(categoria)
                } ?: tareaRepository.obtenerTareasPorRol()

                flow.collectLatest { listaTareas ->
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
            lista             = lista,
            mostrarAcciones   = esLider,
            nombresAsignados  = nombresParticipantes,
            onEditar          = { tarea ->
                if (esLider) {
                    val intent = Intent(this, EditarTareaActivity::class.java)
                    intent.putExtra("TAREA_ID", tarea.id)
                    startActivity(intent)
                }
            },
            onEliminar        = { tarea ->
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
        binding.rvTareas.visibility    = if (vacio) View.GONE else View.VISIBLE
    }

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
