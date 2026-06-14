package com.taskflow.app.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.taskflow.app.databinding.ItemTareaBinding
import com.taskflow.app.model.Tarea

/**
 * Adaptador para la lista de tareas en MainActivity.
 *
 * Parámetro nuevo: mostrarAcciones
 *  - true  → el Líder ve los botones Editar y Eliminar
 *  - false → el Participante solo ve el checkbox y el título
 *
 * Esto evita tener dos adapters distintos para cada rol.
 */
class TareaAdapter(
    private val lista: List<Tarea>,
    private val mostrarAcciones: Boolean = true,
    private val onEditar: (Tarea) -> Unit,
    private val onEliminar: (Tarea) -> Unit,
    private val onCompletarToggle: (Tarea) -> Unit
) : RecyclerView.Adapter<TareaAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemTareaBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTareaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tarea = lista[position]

        holder.binding.tvTituloTarea.text = tarea.titulo
        holder.binding.tvPrioridad.text   = tarea.prioridad
        holder.binding.tvCategoria.text   = tarea.categoria
        holder.binding.checkCompletada.isChecked = tarea.completada

        // Mostrar fecha límite si existe
        if (tarea.fechaLimite.isNotBlank()) {
            holder.binding.tvFechaLimite.visibility = View.VISIBLE
            holder.binding.tvFechaLimite.text = "Límite: ${tarea.fechaLimite}"
        } else {
            holder.binding.tvFechaLimite.visibility = View.GONE
        }

        // Si la tarea está completada, tachar el título
        if (tarea.completada) {
            holder.binding.tvTituloTarea.paintFlags =
                holder.binding.tvTituloTarea.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.tvTituloTarea.alpha = 0.5f
        } else {
            holder.binding.tvTituloTarea.paintFlags =
                holder.binding.tvTituloTarea.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            holder.binding.tvTituloTarea.alpha = 1.0f
        }

        // Color de la etiqueta de prioridad
        val colorPrioridad = when (tarea.prioridad) {
            "Alta"  -> android.graphics.Color.parseColor("#D32F2F")
            "Media" -> android.graphics.Color.parseColor("#F57C00")
            else    -> android.graphics.Color.parseColor("#388E3C")
        }
        holder.binding.tvPrioridad.setTextColor(colorPrioridad)

        // Mostrar u ocultar botones de acción según el rol
        val visibilidadAcciones = if (mostrarAcciones) View.VISIBLE else View.GONE
        holder.binding.btnEditar.visibility   = visibilidadAcciones
        holder.binding.btnEliminar.visibility = visibilidadAcciones

        // Listeners
        holder.binding.btnEditar.setOnClickListener  { onEditar(tarea) }
        holder.binding.btnEliminar.setOnClickListener { onEliminar(tarea) }
        holder.binding.checkCompletada.setOnClickListener { onCompletarToggle(tarea) }
    }

    override fun getItemCount() = lista.size
}
