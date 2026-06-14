package com.taskflow.app.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taskflow.app.data.db.TaskFlowDatabase
import com.taskflow.app.data.repository.AuthRepository
import com.taskflow.app.databinding.ActivityRegistroBinding
import com.taskflow.app.util.SesionManager
import com.taskflow.app.util.ValidadorTarea
import kotlinx.coroutines.launch

/**
 * ─────────────────────────────────────────────
 *  RegistroActivity.kt
 *  Responsable: Jonathan Quinto
 *  Propósito: Registro de nuevo usuario con rol.
 * ─────────────────────────────────────────────
 *
 *  Flujo:
 *  1. Usuario llena nombre, email, contraseña, confirmación
 *  2. Selecciona rol: Líder o Participante (RadioGroup)
 *  3. ValidadorTarea revisa todos los campos
 *  4. AuthRepository verifica email único y registra
 *  5. Si éxito → guarda sesión y va directo a MainActivity
 *     (el usuario no tiene que loguearse después de registrarse)
 */
class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var sesionManager: SesionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
     * btnRegistrar → valida y registra
     * tvLogin      → vuelve a LoginActivity
     */
    private fun configurarBotones() {
        binding.btnRegistrar.setOnClickListener {
            registrarUsuario()
        }

        binding.tvLogin.setOnClickListener {
            finish() // Vuelve al Login sin crear nueva Activity
        }
    }

    private fun registrarUsuario() {
        val nombre           = binding.etNombre.editText?.text.toString().trim()
        val email            = binding.etEmail.editText?.text.toString().trim()
        val password         = binding.etPassword.editText?.text.toString()
        val confirmar        = binding.etConfirmarPassword.editText?.text.toString()

        // Determinar el rol seleccionado en el RadioGroup
        val rol = when (binding.radioGroupRol.checkedRadioButtonId) {
            binding.rbLider.id        -> SesionManager.ROL_LIDER
            binding.rbParticipante.id -> SesionManager.ROL_PARTICIPANTE
            else -> {
                Toast.makeText(this, "Selecciona un rol (Líder o Participante)", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Validar todos los campos
        val esValido = ValidadorTarea.validarRegistro(
            layoutNombre    = binding.etNombre,
            nombre          = nombre,
            layoutEmail     = binding.etEmail,
            email           = email,
            layoutPassword  = binding.etPassword,
            password        = password,
            layoutConfirmar = binding.etConfirmarPassword,
            confirmarPassword = confirmar
        )
        if (!esValido) return

        mostrarCargando(true)

        lifecycleScope.launch {
            val resultado = authRepository.registrar(nombre, email, password, rol)

            mostrarCargando(false)

            when (resultado) {
                is AuthRepository.ResultadoRegistro.Exito -> {
                    // Registro exitoso → guardar sesión y entrar directo
                    sesionManager.guardarSesion(resultado.usuario)

                    Toast.makeText(
                        this@RegistroActivity,
                        "¡Bienvenido, ${resultado.usuario.nombre}!",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(this@RegistroActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

                is AuthRepository.ResultadoRegistro.EmailYaExiste -> {
                    binding.etEmail.error = "Este email ya está registrado"
                    binding.etEmail.requestFocus()
                }

                is AuthRepository.ResultadoRegistro.ErrorBaseDatos -> {
                    Toast.makeText(
                        this@RegistroActivity,
                        "Error al registrar. Intenta de nuevo.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun mostrarCargando(cargando: Boolean) {
        binding.btnRegistrar.isEnabled = !cargando
        binding.progressRegistro.visibility = if (cargando) View.VISIBLE else View.GONE
    }
}
