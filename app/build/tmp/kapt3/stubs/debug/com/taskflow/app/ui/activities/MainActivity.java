package com.taskflow.app.ui.activities;

/**
 * ─────────────────────────────────────────────
 * MainActivity.kt — Pantalla Principal
 * Lógica de botones: Jonathan Quinto
 * ─────────────────────────────────────────────
 *
 * Comportamiento según rol:
 *
 * LÍDER:
 *  - Ve todas las tareas que creó
 *  - Puede agregar, editar, eliminar y asignar
 *  - El FAB (+) está visible
 *  - Cada tarea muestra a quién está asignada
 *  - Estado vacío: "Presiona + para agregar una tarea"
 *
 * PARTICIPANTE:
 *  - Ve solo las tareas asignadas a él
 *  - Puede marcar tareas como completadas
 *  - NO puede crear, editar ni eliminar tareas
 *  - El FAB (+) está oculto
 *  - Estado vacío: "El líder aún no te ha asignado tareas"
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u000e\u001a\u00020\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\u0010\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0012H\u0002J\b\u0010\u0015\u001a\u00020\u000fH\u0002J\b\u0010\u0016\u001a\u00020\u000fH\u0002J\b\u0010\u0017\u001a\u00020\u000fH\u0002J\b\u0010\u0018\u001a\u00020\u000fH\u0002J\u0010\u0010\u0019\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u0012H\u0002J\u0010\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u0006H\u0002J\u0010\u0010\u001c\u001a\u00020\u000f2\u0006\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020\u000fH\u0002J\u0012\u0010 \u001a\u00020\u000f2\b\u0010!\u001a\u0004\u0018\u00010\"H\u0014J\u0010\u0010#\u001a\u00020\u001e2\u0006\u0010$\u001a\u00020%H\u0016J\u0010\u0010&\u001a\u00020\u001e2\u0006\u0010\'\u001a\u00020(H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00060\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006)"}, d2 = {"Lcom/taskflow/app/ui/activities/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/taskflow/app/databinding/ActivityMainBinding;", "filtroCategoria", "", "nombresParticipantes", "", "", "sesionManager", "Lcom/taskflow/app/util/SesionManager;", "tareaRepository", "Lcom/taskflow/app/data/repository/TareaRepository;", "actualizarUI", "", "lista", "", "Lcom/taskflow/app/model/Tarea;", "cambiarEstadoTarea", "tarea", "cargarNombresParticipantes", "configurarBotones", "configurarRecyclerView", "configurarToolbarPorRol", "confirmarEliminar", "mostrarError", "mensaje", "mostrarEstadoVacio", "vacio", "", "observarTareas", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onCreateOptionsMenu", "menu", "Landroid/view/Menu;", "onOptionsItemSelected", "item", "Landroid/view/MenuItem;", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.taskflow.app.databinding.ActivityMainBinding binding;
    private com.taskflow.app.util.SesionManager sesionManager;
    private com.taskflow.app.data.repository.TareaRepository tareaRepository;
    @org.jetbrains.annotations.NotNull()
    private java.util.Map<java.lang.Integer, java.lang.String> nombresParticipantes;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String filtroCategoria;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void configurarToolbarPorRol() {
    }
    
    /**
     * Carga el mapa id→nombre de participantes antes de observar las tareas,
     * para que el adapter pueda mostrar "Asignado a: [nombre]" en cada tarjeta.
     */
    private final void cargarNombresParticipantes() {
    }
    
    private final void configurarRecyclerView() {
    }
    
    private final void observarTareas() {
    }
    
    private final void actualizarUI(java.util.List<com.taskflow.app.model.Tarea> lista) {
    }
    
    private final void mostrarEstadoVacio(boolean vacio) {
    }
    
    private final void configurarBotones() {
    }
    
    private final void confirmarEliminar(com.taskflow.app.model.Tarea tarea) {
    }
    
    private final void cambiarEstadoTarea(com.taskflow.app.model.Tarea tarea) {
    }
    
    private final void mostrarError(java.lang.String mensaje) {
    }
    
    @java.lang.Override()
    public boolean onCreateOptionsMenu(@org.jetbrains.annotations.NotNull()
    android.view.Menu menu) {
        return false;
    }
    
    @java.lang.Override()
    public boolean onOptionsItemSelected(@org.jetbrains.annotations.NotNull()
    android.view.MenuItem item) {
        return false;
    }
}