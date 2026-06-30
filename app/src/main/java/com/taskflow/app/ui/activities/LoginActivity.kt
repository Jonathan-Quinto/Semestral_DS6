package com.taskflow.app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taskflow.app.data.db.TaskFlowDatabase
import com.taskflow.app.data.repository.AuthRepository
import com.taskflow.app.databinding.ActivityLoginBinding
import com.taskflow.app.util.SesionManager
import com.taskflow.app.util.ValidadorTarea
import kotlinx.coroutines.launch


/**
 * ─────────────────────────────────────────────
 *  LoginActivity.kt
 *  Responsable: Jonathan Quinto
 *  Propósito: Pantalla de inicio de sesión.
 * ─────────────────────────────────────────────
 *
 *  Flujo:
 *  1. Usuario ingresa email y contraseña
 *  2. ValidadorTarea verifica formato
 *  3. AuthRepository hashea la contraseña y consulta la BD
 *  4. Si es válido → guarda sesión y va a MainActivity
 *  5. Si no → muestra error específico en el campo
 *
 *  Botón "Regístrate" → lleva a RegistroActivity
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var sesionManager: SesionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sin ActionBar en la pantalla de login
        supportActionBar?.hide()

        val db = TaskFlowDatabase.obtenerInstancia(this)
        authRepository = AuthRepository(db.usuarioDao())
        sesionManager = SesionManager(this)

        configurarBotones()
    }

    /**
     * ── BOTONES ──
     * Responsable: Jonathan Quinto
     *
     * btnLogin  → valida y autentica
     * tvRegistro → navega a registro
     */
    private fun configurarBotones() {
        binding.btnLogin.setOnClickListener {
            iniciarSesion()
        }

        binding.tvRegistro.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }
    }

    private fun iniciarSesion() {
        val email    = binding.etEmail.editText?.text.toString().trim()
        val password = binding.etPassword.editText?.text.toString()

        // Paso 1: Validar formato
        val esValido = ValidadorTarea.validarLogin(
            layoutEmail    = binding.etEmail,
            email          = email,
            layoutPassword = binding.etPassword,
            password       = password
        )
        if (!esValido) return

        // Paso 2: Mostrar cargando
        mostrarCargando(true)

        // Paso 3: Autenticar en la BD
        lifecycleScope.launch {
            val resultado = authRepository.login(email, password)

            mostrarCargando(false)

            when (resultado) {
                is AuthRepository.ResultadoLogin.Exito -> {
                    // Guardar sesión y navegar
                    sesionManager.guardarSesion(resultado.usuario)

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

                is AuthRepository.ResultadoLogin.CredencialesInvalidas -> {
                    // Error específico en el campo contraseña
                    binding.etPassword.error = "Email o contraseña incorrectos"
                    binding.etPassword.requestFocus()
                }

                is AuthRepository.ResultadoLogin.ErrorBaseDatos -> {
                    Toast.makeText(
                        this@LoginActivity,
                        "Error de conexión. Intenta de nuevo.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun mostrarCargando(cargando: Boolean) {
        binding.btnLogin.isEnabled = !cargando
        binding.progressLogin.visibility = if (cargando) View.VISIBLE else View.GONE
    }
}
