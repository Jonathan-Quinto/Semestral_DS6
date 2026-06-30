package com.taskflow.app.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.taskflow.app.adapter.CategoriaAdapter
import com.taskflow.app.data.db.TaskFlowDatabase
import com.taskflow.app.databinding.ActivityCategoriasBinding
import com.taskflow.app.util.SesionManager
import kotlinx.coroutines.launch

/**
 * ─────────────────────────────────────────────
 *  CategoriasActivity.kt — Pantalla Categorías
 * ─────────────────────────────────────────────
 *
 *  Muestra las categorías disponibles con su cantidad
 *  de tareas. Al tocar una categoría filtra la lista.
 *
 *  FIX: Usa el DAO correcto según el rol del usuario
 *  (contarPorCategoriaYLider o contarPorCategoria general).
 */
class CategoriasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriasBinding
    private val db by lazy { TaskFlowDatabase.obtenerInstancia(this) }

    // Categorías fijas de la app
    private val categoriasFijas = listOf("Personal", "Trabajo", "Estudios", "Compras")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Categorías"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.rvCategorias.layoutManager = GridLayoutManager(this, 2)

        cargarCategorias()
    }

    /**
     * Carga cada categoría con su conteo de tareas filtrado por rol.
     */
    private fun cargarCategorias() {
        val sesion = SesionManager(this)
        val usuarioId = sesion.getUsuarioId()

        lifecycleScope.launch {
            try {
                val listaCategorias = categoriasFijas.map { nombre ->
                    // FIX: contar según rol — el Líder ve sus tareas, el Participante las suyas
                    val cantidad = if (sesion.esLider()) {
                        db.tareaDao().contarPorCategoriaYLider(usuarioId, nombre)
                    } else {
                        // Para participante: contar tareas asignadas a él en esa categoría
                        db.tareaDao().contarPorCategoriaYParticipante(usuarioId, nombre)
                    }
                    Pair(nombre, cantidad)
                }

                val adaptador = CategoriaAdapter(listaCategorias) { categoriaSeleccionada ->
                    val intent = Intent(this@CategoriasActivity, MainActivity::class.java)
                    intent.putExtra("FILTRO_CATEGORIA", categoriaSeleccionada)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                }
                binding.rvCategorias.adapter = adaptador

            } catch (e: Exception) {
                com.google.android.material.snackbar.Snackbar
                    .make(binding.root, "Error al cargar categorías", com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
