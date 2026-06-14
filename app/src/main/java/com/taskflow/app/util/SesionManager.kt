package com.taskflow.app.util

import android.content.Context
import android.content.SharedPreferences
import com.taskflow.app.model.Usuario
import java.security.MessageDigest

/**
 * ─────────────────────────────────────────────
 *  SesionManager.kt
 *  Responsable: Jonathan Quinto
 *  Propósito: Gestionar la sesión del usuario activo
 *  y el hashing de contraseñas.
 * ─────────────────────────────────────────────
 *
 *  Guarda en SharedPreferences:
 *   - ID del usuario logueado
 *   - Nombre
 *   - Email
 *   - Rol ("LIDER" o "PARTICIPANTE")
 *
 *  La sesión persiste entre reinicios de la app.
 *  Para cerrar sesión se llama a cerrarSesion().
 *
 *  Uso desde cualquier Activity:
 *      val sesion = SesionManager(this)
 *      val rol = sesion.getRol()          // "LIDER" o "PARTICIPANTE"
 *      val id  = sesion.getUsuarioId()    // Int
 *      val esLider = sesion.esLider()     // Boolean
 */
class SesionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    // ── Guardar sesión ────────────────────────────────────────────────────────

    /**
     * Guarda los datos del usuario que acaba de iniciar sesión.
     * Se llama justo después de que el login es exitoso en el DAO.
     */
    fun guardarSesion(usuario: Usuario) {
        prefs.edit()
            .putInt(KEY_ID, usuario.id)
            .putString(KEY_NOMBRE, usuario.nombre)
            .putString(KEY_EMAIL, usuario.email)
            .putString(KEY_ROL, usuario.rol)
            .apply()
    }

    // ── Leer datos de sesión ──────────────────────────────────────────────────

    /** ID del usuario logueado. Devuelve -1 si no hay sesión activa. */
    fun getUsuarioId(): Int = prefs.getInt(KEY_ID, -1)

    /** Nombre del usuario logueado. */
    fun getNombre(): String = prefs.getString(KEY_NOMBRE, "") ?: ""

    /** Email del usuario logueado. */
    fun getEmail(): String = prefs.getString(KEY_EMAIL, "") ?: ""

    /** Rol del usuario logueado: "LIDER" o "PARTICIPANTE". */
    fun getRol(): String = prefs.getString(KEY_ROL, "") ?: ""

    /** true si el usuario activo es Líder. */
    fun esLider(): Boolean = getRol() == ROL_LIDER

    /** true si el usuario activo es Participante. */
    fun esParticipante(): Boolean = getRol() == ROL_PARTICIPANTE

    /** true si hay una sesión activa (usuario logueado). */
    fun haySesion(): Boolean = getUsuarioId() != -1

    // ── Cerrar sesión ─────────────────────────────────────────────────────────

    /**
     * Limpia todos los datos de sesión.
     * Después de esto haySesion() devuelve false
     * y SplashActivity redirige al Login.
     */
    fun cerrarSesion() {
        prefs.edit().clear().apply()
    }

    // ── Hashing de contraseñas ────────────────────────────────────────────────

    companion object {
        private const val PREFS_NAME = "taskflow_sesion"
        private const val KEY_ID     = "usuario_id"
        private const val KEY_NOMBRE = "usuario_nombre"
        private const val KEY_EMAIL  = "usuario_email"
        private const val KEY_ROL    = "usuario_rol"

        const val ROL_LIDER        = "LIDER"
        const val ROL_PARTICIPANTE = "PARTICIPANTE"

        /**
         * Hashea una contraseña con SHA-256.
         *
         * ¿Por qué SHA-256 y no BCrypt?
         * BCrypt requiere una librería externa. SHA-256 está disponible en Android
         * sin dependencias adicionales y es suficiente para un proyecto universitario.
         * En producción real se usaría BCrypt o Argon2.
         *
         * @param password Contraseña en texto plano
         * @return Hash en hexadecimal de 64 caracteres
         */
        fun hashPassword(password: String): String {
            val bytes = MessageDigest
                .getInstance("SHA-256")
                .digest(password.toByteArray(Charsets.UTF_8))
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }
}
