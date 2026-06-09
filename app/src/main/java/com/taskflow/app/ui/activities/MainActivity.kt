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
import com.taskflow.app.databinding.ActivityMainBinding
import com.taskflow.app.model.Tarea
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * ─────────────────────────────────────────────
 *  MainActivity.kt — Pantalla Principal
 *  Lógica de botones: Jonathan Quinto
 * ─────────────────────────────────────────────
 *
 *  Responsabilidades de esta pantalla:
 *  - Mostrar la lista de tareas en tiempo real
 *  - Botón FAB (+) para agregar nueva tarea
 *  - Menú con acceso a Categorías y Estadísticas
 *  - Acciones de cada tarea: Editar, Eliminar, Completar
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Acceso a la base de datos Room
    private val db by lazy { TaskFlowDatabase.obtenerInstancia(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        configurarRecyclerView()
        observarTareas()
        configurarBotones()
    }

    /**
     * Configura el RecyclerView con su adaptador y layout manager.
     */
    private fun configurarRecyclerView() {
        binding.rvTareas.layoutManager = LinearLayoutManager(this)
    }

    /**
     * Observa la base de datos en tiempo real.
     * Cada vez que cambia una tarea, la lista se actualiza sola.
     */
    private fun observarTareas() {
        lifecycleScope.launch {
            try {
                db.tareaDao().obtenerTodas().collectLatest { listaTareas ->
                    actualizarUI(listaTareas)
                }
            } catch (e: Exception) {
                // Si falla la DB, mostramos el estado vacío en lugar de crashear
                mostrarEstadoVacio(true)
            }
        }
    }

    /**
     * Actualiza la interfaz según si hay tareas o no.
     * - Sin tareas: muestra el mensaje "No hay tareas, presiona + para agregar"
     * - Con tareas: muestra el RecyclerView con la lista
     */
    private fun actualizarUI(lista: List<Tarea>) {
        if (lista.isEmpty()) {
            mostrarEstadoVacio(true)
        } else {
            mostrarEstadoVacio(false)
            val adaptador = TareaAdapter(
                lista = lista,
                // ── Acción EDITAR ──
                onEditar = { tarea ->
                    val intent = Intent(this, EditarTareaActivity::class.java)
                    intent.putExtra("TAREA_ID", tarea.id)
                    startActivity(intent)
                },
                // ── Acción ELIMINAR ──
                onEliminar = { tarea ->
                    confirmarEliminar(tarea)
                },
                // ── Acción COMPLETAR (checkbox) ──
                onCompletarToggle = { tarea ->
                    cambiarEstadoTarea(tarea)
                }
            )
            binding.rvTareas.adapter = adaptador
        }
    }

    /**
     * Muestra u oculta el estado vacío y el RecyclerView.
     */
    private fun mostrarEstadoVacio(vacio: Boolean) {
        binding.layoutVacio.visibility = if (vacio) View.VISIBLE else View.GONE
        binding.rvTareas.visibility = if (vacio) View.GONE else View.VISIBLE
    }

    /**
     * ── BOTONES DE LA PANTALLA ──
     * Responsable: Jonathan Quinto
     *
     * FAB (+): abre la pantalla de agregar tarea
     */
    private fun configurarBotones() {
        binding.fabAgregarTarea.setOnClickListener {
            startActivity(Intent(this, AgregarTareaActivity::class.java))
        }
    }

    /**
     * Muestra un diálogo de confirmación antes de eliminar.
     * Evita eliminaciones accidentales.
     */
    private fun confirmarEliminar(tarea: Tarea) {
        android.app.AlertDialog.Builder(this)
            .setTitle("Eliminar tarea")
            .setMessage("¿Deseas eliminar \"${tarea.titulo}\"? Esta acción no se puede deshacer.")
            .setPositiveButton("Eliminar") { _, _ ->
                lifecycleScope.launch {
                    try {
                        db.tareaDao().eliminar(tarea)
                    } catch (e: Exception) {
                        mostrarError("No se pudo eliminar la tarea. Intenta de nuevo.")
                    }
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    /**
     * Cambia el estado de una tarea entre pendiente y completada.
     * Invierte el valor actual del campo 'completada'.
     */
    private fun cambiarEstadoTarea(tarea: Tarea) {
        lifecycleScope.launch {
            try {
                val tareaActualizada = tarea.copy(completada = !tarea.completada)
                db.tareaDao().actualizar(tareaActualizada)
            } catch (e: Exception) {
                mostrarError("No se pudo actualizar la tarea.")
            }
        }
    }

    /**
     * Muestra un mensaje de error como Snackbar al usuario.
     */
    private fun mostrarError(mensaje: String) {
        com.google.android.material.snackbar.Snackbar
            .make(binding.root, mensaje, com.google.android.material.snackbar.Snackbar.LENGTH_LONG)
            .show()
    }

    /**
     * Menú de tres puntos — acceso a Categorías y Estadísticas
     */
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
            else -> super.onOptionsItemSelected(item)
        }
    }
}
