package com.taskflow.app.util

import com.google.android.material.textfield.TextInputLayout

/**
 * ─────────────────────────────────────────────
 *  ValidadorTarea.kt
 *  Responsable: Jonathan Quinto
 *  Propósito: Centralizar todas las validaciones
 *  de formularios de la app TaskFlow.
 * ─────────────────────────────────────────────
 *
 *  ¿Por qué existe esta clase?
 *  En lugar de copiar las mismas validaciones en
 *  AgregarActivity y EditarActivity, las ponemos
 *  aquí una sola vez. Así si algo cambia, lo
 *  cambiamos en un solo lugar.
 */
object ValidadorTarea {

    /**
     * Valida el formulario completo de una tarea.
     *
     * @param layoutTitulo  El TextInputLayout del campo título
     * @param titulo        El texto ingresado en el título
     * @return true si todo está bien, false si hay algún error
     *
     * ¿Cómo funciona?
     * 1. Limpia errores anteriores
     * 2. Revisa el título (campo obligatorio)
     * 3. Si hay error, lo muestra en el campo y devuelve false
     * 4. Si todo está bien, devuelve true
     */
    fun validarFormulario(
        layoutTitulo: TextInputLayout,
        titulo: String
    ): Boolean {

        // Paso 1: Limpiar errores anteriores para que no queden mensajes viejos
        limpiarErrores(layoutTitulo)

        // Paso 2: Validar que el título no esté vacío
        if (titulo.isBlank()) {
            layoutTitulo.error = "El título no puede estar vacío"
            layoutTitulo.requestFocus()
            return false
        }

        // Paso 3: Validar longitud mínima (al menos 3 caracteres)
        if (titulo.trim().length < 3) {
            layoutTitulo.error = "El título debe tener al menos 3 caracteres"
            layoutTitulo.requestFocus()
            return false
        }

        // Paso 4: Validar longitud máxima (no más de 100 caracteres)
        if (titulo.trim().length > 100) {
            layoutTitulo.error = "El título no puede superar los 100 caracteres"
            layoutTitulo.requestFocus()
            return false
        }

        // Todo correcto
        return true
    }

    /**
     * Limpia los mensajes de error de los campos.
     * Se llama antes de cada nueva validación.
     */
    fun limpiarErrores(vararg layouts: TextInputLayout) {
        for (layout in layouts) {
            layout.error = null
            layout.isErrorEnabled = false
        }
    }
}
