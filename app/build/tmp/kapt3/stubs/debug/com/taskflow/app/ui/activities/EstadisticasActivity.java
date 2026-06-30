package com.taskflow.app.ui.activities;

/**
 * ─────────────────────────────────────────────
 * EstadisticasActivity.kt
 * ─────────────────────────────────────────────
 *
 * Muestra estadísticas según el rol:
 *
 * LÍDER:
 *  - Total de tareas creadas
 *  - Completadas / Pendientes
 *  - Sin asignar
 *  - Porcentaje de progreso del equipo
 *  - Conteo por categoría (solo las que él creó)
 *
 * PARTICIPANTE:
 *  - Total de tareas asignadas a él
 *  - Completadas / Pendientes
 *  - Porcentaje de progreso personal
 *  - Sección "Sin asignar" oculta
 *  - Conteo por categoría (solo las que le asignaron)
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0002J\u0012\u0010\u000b\u001a\u00020\n2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u0014J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/taskflow/app/ui/activities/EstadisticasActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/taskflow/app/databinding/ActivityEstadisticasBinding;", "sesionManager", "Lcom/taskflow/app/util/SesionManager;", "tareaRepository", "Lcom/taskflow/app/data/repository/TareaRepository;", "cargarEstadisticas", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "", "app_debug"})
public final class EstadisticasActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.taskflow.app.databinding.ActivityEstadisticasBinding binding;
    private com.taskflow.app.data.repository.TareaRepository tareaRepository;
    private com.taskflow.app.util.SesionManager sesionManager;
    
    public EstadisticasActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void cargarEstadisticas() {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
}