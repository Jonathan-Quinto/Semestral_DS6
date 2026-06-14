package com.taskflow.app.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taskflow.app.databinding.ActivityPerfilBinding
import com.taskflow.app.util.SesionManager

/**
 * ─────────────────────────────────────────────
 *  PerfilActivity.kt — Perfil del usuario
 *  Responsable: Jonathan Quinto
 * ─────────────────────────────────────────────
 *
 *  Muestra:
 *   - Nombre del usuario activo
 *   - Email
 *   - Rol (Líder / Participante)
 *   - Fecha de registro (si la UI la tiene)
 *
 *  Botón "Cerrar sesión":
 *   → Limpia SharedPreferences
 *   → Redirige a LoginActivity limpiando el back stack
 *     (el usuario no puede volver con el botón Atrás)
 */
class PerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    private lateinit var sesionManager: SesionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Mi perfil"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sesionManager = SesionManager(this)

        mostrarDatosPerfil()
        configurarBotones()
    }

    /**
     * Muestra los datos del usuario activo en la UI.
     * Los datos vienen de SharedPreferences (SesionManager), sin consultar la BD.
     */
    private fun mostrarDatosPerfil() {
        binding.tvNombrePerfil.text = sesionManager.getNombre()
        binding.tvEmailPerfil.text  = sesionManager.getEmail()
        binding.tvRolPerfil.text    = when (sesionManager.getRol()) {
            SesionManager.ROL_LIDER        -> "Líder"
            SesionManager.ROL_PARTICIPANTE -> "Participante"
            else -> sesionManager.getRol()
        }
    }

    /**
     * ── BOTÓN CERRAR SESIÓN ──
     * Responsable: Jonathan Quinto
     *
     * 1. Muestra diálogo de confirmación (evita cierres accidentales)
     * 2. Si confirma → limpia la sesión → va a LoginActivity
     * 3. FLAG_ACTIVITY_CLEAR_TASK: el usuario no puede volver con Atrás
     */
    private fun configurarBotones() {
        binding.btnCerrarSesion.setOnClickListener {
            android.app.AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Seguro que quieres cerrar sesión?")
                .setPositiveButton("Cerrar sesión") { _, _ ->
                    sesionManager.cerrarSesion()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
