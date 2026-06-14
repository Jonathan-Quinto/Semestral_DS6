package com.taskflow.app.util

import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout

/**
 * ─────────────────────────────────────────────
 *  ValidadorTarea.kt
 *  Responsable: Jonathan Quinto
 *  Propósito: Centralizar todas las validaciones
 *  de formularios de la app TaskFlow.
 * ─────────────────────────────────────────────
 *
 *  Valida:
 *   - Formulario de tarea (título, descripción)
 *   - Formulario de login (email, contraseña)
 *   - Formulario de registro (nombre, email, contraseña, confirmación)
 */
object ValidadorTarea {

    // ── Formulario de tarea ───────────────────────────────────────────────────

    /**
     * Valida el formulario completo de una tarea.
     *
     * @param layoutTitulo  El TextInputLayout del campo título
     * @param titulo        El texto ingresado en el título
     * @return true si todo está bien, false si hay algún error
     */
    fun validarFormulario(
        layoutTitulo: TextInputLayout,
        titulo: String
    ): Boolean {
        limpiarErrores(layoutTitulo)

        if (titulo.isBlank()) {
            layoutTitulo.error = "El título no puede estar vacío"
            layoutTitulo.requestFocus()
            return false
        }

        if (titulo.trim().length < 3) {
            layoutTitulo.error = "El título debe tener al menos 3 caracteres"
            layoutTitulo.requestFocus()
            return false
        }

        if (titulo.trim().length > 100) {
            layoutTitulo.error = "El título no puede superar los 100 caracteres"
            layoutTitulo.requestFocus()
            return false
        }

        return true
    }

    // ── Formulario de login ───────────────────────────────────────────────────

    /**
     * Valida el formulario de inicio de sesión.
     *
     * @return true si email y contraseña tienen formato válido
     */
    fun validarLogin(
        layoutEmail: TextInputLayout,
        email: String,
        layoutPassword: TextInputLayout,
        password: String
    ): Boolean {
        limpiarErrores(layoutEmail, layoutPassword)
        var esValido = true

        if (email.isBlank()) {
            layoutEmail.error = "Ingresa tu email"
            esValido = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            layoutEmail.error = "El email no tiene un formato válido"
            esValido = false
        }

        if (password.isBlank()) {
            layoutPassword.error = "Ingresa tu contraseña"
            if (esValido) layoutPassword.requestFocus()
            esValido = false
        } else if (password.length < 6) {
            layoutPassword.error = "La contraseña debe tener al menos 6 caracteres"
            if (esValido) layoutPassword.requestFocus()
            esValido = false
        }

        if (!esValido && email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            layoutPassword.requestFocus()
        } else if (!esValido) {
            layoutEmail.requestFocus()
        }

        return esValido
    }

    // ── Formulario de registro ────────────────────────────────────────────────

    /**
     * Valida el formulario de registro de nuevo usuario.
     *
     * @return true si todos los campos son válidos
     */
    fun validarRegistro(
        layoutNombre: TextInputLayout,
        nombre: String,
        layoutEmail: TextInputLayout,
        email: String,
        layoutPassword: TextInputLayout,
        password: String,
        layoutConfirmar: TextInputLayout,
        confirmarPassword: String
    ): Boolean {
        limpiarErrores(layoutNombre, layoutEmail, layoutPassword, layoutConfirmar)
        var esValido = true
        var primerCampoConError: TextInputLayout? = null

        if (nombre.isBlank()) {
            layoutNombre.error = "Ingresa tu nombre"
            esValido = false
            primerCampoConError = primerCampoConError ?: layoutNombre
        } else if (nombre.trim().length < 2) {
            layoutNombre.error = "El nombre debe tener al menos 2 caracteres"
            esValido = false
            primerCampoConError = primerCampoConError ?: layoutNombre
        }

        if (email.isBlank()) {
            layoutEmail.error = "Ingresa tu email"
            esValido = false
            primerCampoConError = primerCampoConError ?: layoutEmail
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()) {
            layoutEmail.error = "El email no tiene un formato válido"
            esValido = false
            primerCampoConError = primerCampoConError ?: layoutEmail
        }

        if (password.isBlank()) {
            layoutPassword.error = "Ingresa una contraseña"
            esValido = false
            primerCampoConError = primerCampoConError ?: layoutPassword
        } else if (password.length < 6) {
            layoutPassword.error = "La contraseña debe tener al menos 6 caracteres"
            esValido = false
            primerCampoConError = primerCampoConError ?: layoutPassword
        }

        if (confirmarPassword.isBlank()) {
            layoutConfirmar.error = "Confirma tu contraseña"
            esValido = false
            primerCampoConError = primerCampoConError ?: layoutConfirmar
        } else if (password != confirmarPassword) {
            layoutConfirmar.error = "Las contraseñas no coinciden"
            esValido = false
            primerCampoConError = primerCampoConError ?: layoutConfirmar
        }

        primerCampoConError?.requestFocus()
        return esValido
    }

    // ── Utilidad ──────────────────────────────────────────────────────────────

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
