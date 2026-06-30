package com.taskflow.app.ui.activities;

/**
 * ─────────────────────────────────────────────
 * AgregarTareaActivity.kt — Pantalla Agregar
 * Lógica y validaciones: Jonathan Quinto
 * ─────────────────────────────────────────────
 *
 * Solo accesible para el Líder (el FAB está oculto
 * para los Participantes en MainActivity).
 *
 * Flujo:
 * 1. Líder llena título, descripción, prioridad, categoría
 * 2. Selecciona a cuál participante asignar (spinner)
 *    - Si no hay participantes → puede guardar sin asignar
 * 3. Presiona GUARDAR
 * 4. TareaRepository crea la tarea con creadaPor automático
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\n\u001a\u00020\u000bH\u0002J\b\u0010\f\u001a\u00020\u000bH\u0002J\b\u0010\r\u001a\u00020\u000bH\u0002J\b\u0010\u000e\u001a\u00020\u000bH\u0002J\u0012\u0010\u000f\u001a\u00020\u000b2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0014J\b\u0010\u0012\u001a\u00020\u0013H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/taskflow/app/ui/activities/AgregarTareaActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/taskflow/app/databinding/ActivityAgregarTareaBinding;", "participantes", "", "Lcom/taskflow/app/model/Usuario;", "tareaRepository", "Lcom/taskflow/app/data/repository/TareaRepository;", "cargarParticipantes", "", "configurarBotones", "configurarSpinners", "guardarTarea", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "", "app_debug"})
public final class AgregarTareaActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.taskflow.app.databinding.ActivityAgregarTareaBinding binding;
    private com.taskflow.app.data.repository.TareaRepository tareaRepository;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.taskflow.app.model.Usuario> participantes;
    
    public AgregarTareaActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void configurarSpinners() {
    }
    
    /**
     * Carga los participantes disponibles para el spinner de asignación.
     * Si no hay ninguno, oculta el spinner y muestra un mensaje.
     */
    private final void cargarParticipantes() {
    }
    
    /**
     * ── BOTÓN GUARDAR ──
     * Responsable: Jonathan Quinto
     */
    private final void configurarBotones() {
    }
    
    private final void guardarTarea() {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
}