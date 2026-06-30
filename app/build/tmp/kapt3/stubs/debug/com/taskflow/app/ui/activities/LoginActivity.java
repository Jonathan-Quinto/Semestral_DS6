package com.taskflow.app.ui.activities;

/**
 * ─────────────────────────────────────────────
 * LoginActivity.kt
 * Responsable: Jonathan Quinto
 * Propósito: Pantalla de inicio de sesión.
 * ─────────────────────────────────────────────
 *
 * Flujo:
 * 1. Usuario ingresa email y contraseña
 * 2. ValidadorTarea verifica formato
 * 3. AuthRepository hashea la contraseña y consulta la BD
 * 4. Si es válido → guarda sesión y va a MainActivity
 * 5. Si no → muestra error específico en el campo
 *
 * Botón "Regístrate" → lleva a RegistroActivity
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\b\u0010\u000b\u001a\u00020\nH\u0002J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\n2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/taskflow/app/ui/activities/LoginActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "authRepository", "Lcom/taskflow/app/data/repository/AuthRepository;", "binding", "Lcom/taskflow/app/databinding/ActivityLoginBinding;", "sesionManager", "Lcom/taskflow/app/util/SesionManager;", "configurarBotones", "", "iniciarSesion", "mostrarCargando", "cargando", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_debug"})
public final class LoginActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.taskflow.app.databinding.ActivityLoginBinding binding;
    private com.taskflow.app.data.repository.AuthRepository authRepository;
    private com.taskflow.app.util.SesionManager sesionManager;
    
    public LoginActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * ── BOTONES ──
     * Responsable: Jonathan Quinto
     *
     * btnLogin  → valida y autentica
     * tvRegistro → navega a registro
     */
    private final void configurarBotones() {
    }
    
    private final void iniciarSesion() {
    }
    
    private final void mostrarCargando(boolean cargando) {
    }
}