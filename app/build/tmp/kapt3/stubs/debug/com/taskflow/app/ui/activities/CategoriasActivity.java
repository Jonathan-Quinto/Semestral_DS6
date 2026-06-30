package com.taskflow.app.ui.activities;

/**
 * ─────────────────────────────────────────────
 * CategoriasActivity.kt — Pantalla Categorías
 * ─────────────────────────────────────────────
 *
 * Muestra las categorías disponibles con su cantidad
 * de tareas. Al tocar una categoría filtra la lista.
 *
 * FIX: Usa el DAO correcto según el rol del usuario
 * (contarPorCategoriaYLider o contarPorCategoria general).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0002J\u0012\u0010\u0010\u001a\u00020\u000f2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0014J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0015"}, d2 = {"Lcom/taskflow/app/ui/activities/CategoriasActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/taskflow/app/databinding/ActivityCategoriasBinding;", "categoriasFijas", "", "", "db", "Lcom/taskflow/app/data/db/TaskFlowDatabase;", "getDb", "()Lcom/taskflow/app/data/db/TaskFlowDatabase;", "db$delegate", "Lkotlin/Lazy;", "cargarCategorias", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "", "app_debug"})
public final class CategoriasActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.taskflow.app.databinding.ActivityCategoriasBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy db$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> categoriasFijas = null;
    
    public CategoriasActivity() {
        super();
    }
    
    private final com.taskflow.app.data.db.TaskFlowDatabase getDb() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    /**
     * Carga cada categoría con su conteo de tareas filtrado por rol.
     */
    private final void cargarCategorias() {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
}