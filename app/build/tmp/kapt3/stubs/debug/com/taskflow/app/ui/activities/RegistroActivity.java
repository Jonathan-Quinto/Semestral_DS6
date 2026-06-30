package com.taskflow.app.ui.activities;

/**
 * ─────────────────────────────────────────────
 * RegistroActivity.kt
 * Responsable: Jonathan Quinto
 * Propósito: Registro de nuevo usuario con rol.
 * ─────────────────────────────────────────────
 *
 * Flujo:
 * 1. Usuario llena nombre, email, contraseña, confirmación
 * 2. Selecciona rol: Líder o Participante (RadioGroup)
 * 3. ValidadorTarea revisa todos los campos
 * 4. AuthRepository verifica email único y registra
 * 5. Si éxito → guarda sesión y va directo a MainActivity
 *    (el usuario no tiene que loguearse después de registrarse)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0012\u0010\u000e\u001a\u00020\n2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0014J\b\u0010\u0011\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/taskflow/app/ui/activities/RegistroActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "authRepository", "Lcom/taskflow/app/data/repository/AuthRepository;", "binding", "Lcom/taskflow/app/databinding/ActivityRegistroBinding;", "sesionManager", "Lcom/taskflow/app/util/SesionManager;", "configurarBotones", "", "mostrarCargando", "cargando", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "registrarUsuario", "app_debug"})
public final class RegistroActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.taskflow.app.databinding.ActivityRegistroBinding binding;
    private com.taskflow.app.data.repository.AuthRepository authRepository;
    private com.taskflow.app.util.SesionManager sesionManager;
    
    public RegistroActivity() {
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
     * btnRegistrar → valida y registra
     * tvLogin      → vuelve a LoginActivity
     */
    private final void configurarBotones() {
    }
    
    private final void registrarUsuario() {
    }
    
    private final void mostrarCargando(boolean cargando) {
    }
}