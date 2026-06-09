package com.taskflow.app.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.taskflow.app.adapter.CategoriaAdapter
import com.taskflow.app.data.db.TaskFlowDatabase
import com.taskflow.app.databinding.ActivityCategoriasBinding
import kotlinx.coroutines.launch

/**
 * ─────────────────────────────────────────────
 *  CategoriasActivity.kt — Pantalla Categorías
 * ─────────────────────────────────────────────
 *
 *  Muestra las categorías disponibles con su cantidad
 *  de tareas. Al tocar una categoría filtra la lista.
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
     * Carga cada categoría con su conteo de tareas.
     * Usa try-catch por si la base de datos falla.
     */
    private fun cargarCategorias() {
        lifecycleScope.launch {
            try {
                val listaCategorias = categoriasFijas.map { nombre ->
                    val cantidad = db.tareaDao().contarPorCategoria(nombre)
                    Pair(nombre, cantidad)
                }

                val adaptador = CategoriaAdapter(listaCategorias) { categoriaSeleccionada ->
                    // Al tocar una categoría, ir a pantalla filtrada
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
