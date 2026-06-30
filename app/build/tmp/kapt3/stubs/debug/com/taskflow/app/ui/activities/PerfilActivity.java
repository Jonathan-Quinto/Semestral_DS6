package com.taskflow.app.ui.activities;

/**
 * ─────────────────────────────────────────────
 * PerfilActivity.kt — Perfil del usuario
 * Responsable: Jonathan Quinto
 * ─────────────────────────────────────────────
 *
 * Muestra:
 *  - Nombre del usuario activo
 *  - Email
 *  - Rol (Líder / Participante)
 *  - Fecha de registro (si la UI la tiene)
 *
 * Botón "Cerrar sesión":
 *  → Limpia SharedPreferences
 *  → Redirige a LoginActivity limpiando el back stack
 *    (el usuario no puede volver con el botón Atrás)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0002J\b\u0010\t\u001a\u00020\bH\u0002J\u0012\u0010\n\u001a\u00020\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0014J\b\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/taskflow/app/ui/activities/PerfilActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/taskflow/app/databinding/ActivityPerfilBinding;", "sesionManager", "Lcom/taskflow/app/util/SesionManager;", "configurarBotones", "", "mostrarDatosPerfil", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "", "app_debug"})
public final class PerfilActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.taskflow.app.databinding.ActivityPerfilBinding binding;
    private com.taskflow.app.util.SesionManager sesionManager;
    
    public PerfilActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * Muestra los datos del usuario activo en la UI.
     * Los datos vienen de SharedPreferences (SesionManager), sin consultar la BD.
     */
    private final void mostrarDatosPerfil() {
    }
    
    /**
     * ── BOTÓN CERRAR SESIÓN ──
     * Responsable: Jonathan Quinto
     *
     * 1. Muestra diálogo de confirmación (evita cierres accidentales)
     * 2. Si confirma → limpia la sesión → va a LoginActivity
     * 3. FLAG_ACTIVITY_CLEAR_TASK: el usuario no puede volver con Atrás
     */
    private final void configurarBotones() {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
}