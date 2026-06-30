package com.taskflow.app.ui.activities;

/**
 * ─────────────────────────────────────────────
 * EditarTareaActivity.kt — Pantalla Editar
 * Lógica y validaciones: Jonathan Quinto
 * ─────────────────────────────────────────────
 *
 * Flujo:
 * 1. Recibe el ID de la tarea desde MainActivity
 * 2. Carga los datos actuales (título, desc, prioridad, categoría,
 *    fecha límite y asignación actual)
 * 3. El Líder puede reasignar la tarea a otro participante
 * 4. Presiona ACTUALIZAR → valida y guarda con TareaRepository
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\rH\u0002J\b\u0010\u0012\u001a\u00020\rH\u0002J\u0012\u0010\u0013\u001a\u00020\r2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0014J\b\u0010\u0016\u001a\u00020\u0017H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2 = {"Lcom/taskflow/app/ui/activities/EditarTareaActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/taskflow/app/databinding/ActivityEditarTareaBinding;", "participantes", "", "Lcom/taskflow/app/model/Usuario;", "tareaOriginal", "Lcom/taskflow/app/model/Tarea;", "tareaRepository", "Lcom/taskflow/app/data/repository/TareaRepository;", "actualizarTarea", "", "cargarTareaYParticipantes", "id", "", "configurarBotones", "configurarSpinners", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "", "app_debug"})
public final class EditarTareaActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.taskflow.app.databinding.ActivityEditarTareaBinding binding;
    private com.taskflow.app.data.repository.TareaRepository tareaRepository;
    @org.jetbrains.annotations.Nullable()
    private com.taskflow.app.model.Tarea tareaOriginal;
    @org.jetbrains.annotations.NotNull()
    private java.util.List<com.taskflow.app.model.Usuario> participantes;
    
    public EditarTareaActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void configurarSpinners() {
    }
    
    /**
     * Carga la tarea y los participantes disponibles en paralelo.
     */
    private final void cargarTareaYParticipantes(int id) {
    }
    
    /**
     * ── BOTÓN ACTUALIZAR ──
     * Responsable: Jonathan Quinto
     */
    private final void configurarBotones() {
    }
    
    private final void actualizarTarea() {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
}