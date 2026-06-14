package com.taskflow.app.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.taskflow.app.databinding.ActivitySplashBinding
import com.taskflow.app.util.SesionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * SplashActivity — pantalla de bienvenida.
 *
 * Al terminar el delay, decide a dónde ir:
 *  - Si hay sesión activa → MainActivity (pantalla principal)
 *  - Si no hay sesión     → LoginActivity
 *
 * Esto evita que el usuario tenga que loguearse cada vez
 * que abre la app, y también protege MainActivity de
 * accesos sin autenticación.
 */
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sesion = SesionManager(this)

        lifecycleScope.launch {
            delay(2000)

            val destino = if (sesion.haySesion()) {
                // Sesión activa → ir directo a la app
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                // Sin sesión → pedir login
                Intent(this@SplashActivity, LoginActivity::class.java)
            }

            startActivity(destino)
            finish()
        }
    }
}
