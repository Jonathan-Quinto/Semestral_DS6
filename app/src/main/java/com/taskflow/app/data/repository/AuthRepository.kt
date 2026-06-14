package com.taskflow.app.data.repository

import com.taskflow.app.data.db.UsuarioDao
import com.taskflow.app.model.Usuario
import com.taskflow.app.util.SesionManager
import java.text.SimpleDateFormat
import java.util.*

/**
 * ─────────────────────────────────────────────
 *  AuthRepository.kt
 *  Responsable: Jonathan Quinto
 *  Propósito: Centralizar toda la lógica de
 *  autenticación — registro, login y validaciones.
 * ─────────────────────────────────────────────
 *
 *  Las Activities nunca llaman al DAO directamente.
 *  Pasan por aquí para:
 *   1. Validar los datos antes de tocar la BD
 *   2. Hashear la contraseña antes de guardar/comparar
 *   3. Devolver un resultado tipado (sealed class)
 *      para que la UI sepa exactamente qué pasó
 */
class AuthRepository(private val usuarioDao: UsuarioDao) {

    // ── Resultados tipados ────────────────────────────────────────────────────

    /** Resultado del intento de registro */
    sealed class ResultadoRegistro {
        data class Exito(val usuario: Usuario) : ResultadoRegistro()
        object EmailYaExiste : ResultadoRegistro()
        object ErrorBaseDatos : ResultadoRegistro()
    }

    /** Resultado del intento de login */
    sealed class ResultadoLogin {
        data class Exito(val usuario: Usuario) : ResultadoLogin()
        object CredencialesInvalidas : ResultadoLogin()
        object ErrorBaseDatos : ResultadoLogin()
    }

    // ── Registro ──────────────────────────────────────────────────────────────

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * Flujo:
     * 1. Verifica que el email no esté ya registrado
     * 2. Hashea la contraseña con SHA-256
     * 3. Inserta el usuario en la BD
     * 4. Devuelve el resultado tipado para que la UI reaccione
     *
     * @param nombre   Nombre completo del usuario
     * @param email    Email (debe ser único)
     * @param password Contraseña en texto plano (se hashea aquí)
     * @param rol      "LIDER" o "PARTICIPANTE"
     */
    suspend fun registrar(
        nombre: String,
        email: String,
        password: String,
        rol: String
    ): ResultadoRegistro {
        return try {
            // Paso 1: ¿Ya existe este email?
            val existe = usuarioDao.existeEmail(email.trim().lowercase())
            if (existe > 0) return ResultadoRegistro.EmailYaExiste

            // Paso 2: Hashear contraseña
            val hash = SesionManager.hashPassword(password)

            // Paso 3: Crear entidad
            val fechaActual = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
            val nuevoUsuario = Usuario(
                nombre = nombre.trim(),
                email = email.trim().lowercase(),
                passwordHash = hash,
                rol = rol,
                fechaRegistro = fechaActual
            )

            // Paso 4: Insertar — IGNORE devuelve -1 si falla por duplicado
            val idGenerado = usuarioDao.insertar(nuevoUsuario)
            if (idGenerado == -1L) return ResultadoRegistro.EmailYaExiste

            // Paso 5: Recuperar el usuario con su ID real para la sesión
            val usuarioGuardado = nuevoUsuario.copy(id = idGenerado.toInt())
            ResultadoRegistro.Exito(usuarioGuardado)

        } catch (e: Exception) {
            ResultadoRegistro.ErrorBaseDatos
        }
    }

    // ── Login ─────────────────────────────────────────────────────────────────

    /**
     * Autentica un usuario con email y contraseña.
     *
     * Flujo:
     * 1. Hashea la contraseña ingresada
     * 2. Busca en la BD un usuario con ese email + hash
     * 3. Si existe → Exito; si no → CredencialesInvalidas
     *
     * Nota: El email se normaliza a minúsculas para evitar
     * problemas de "User@Email.com" vs "user@email.com".
     *
     * @param email    Email ingresado en el formulario
     * @param password Contraseña en texto plano
     */
    suspend fun login(email: String, password: String): ResultadoLogin {
        return try {
            val hash = SesionManager.hashPassword(password)
            val usuario = usuarioDao.login(email.trim().lowercase(), hash)

            if (usuario != null) {
                ResultadoLogin.Exito(usuario)
            } else {
                ResultadoLogin.CredencialesInvalidas
            }
        } catch (e: Exception) {
            ResultadoLogin.ErrorBaseDatos
        }
    }
}
