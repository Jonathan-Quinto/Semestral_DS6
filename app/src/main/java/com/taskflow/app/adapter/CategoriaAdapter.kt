package com.taskflow.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taskflow.app.databinding.ItemCategoriaBinding

/**
 * Adaptador para la grilla de categorías.
 * Muestra nombre de la categoría y cantidad de tareas.
 */
class CategoriaAdapter(
    private val lista: List<Pair<String, Int>>,
    private val onSeleccionar: (String) -> Unit
) : RecyclerView.Adapter<CategoriaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemCategoriaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoriaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (nombre, cantidad) = lista[position]
        holder.binding.tvNombreCategoria.text = nombre
        holder.binding.tvCantidadTareas.text = "$cantidad Tareas"
        holder.itemView.setOnClickListener { onSeleccionar(nombre) }
    }

    override fun getItemCount() = lista.size
}
