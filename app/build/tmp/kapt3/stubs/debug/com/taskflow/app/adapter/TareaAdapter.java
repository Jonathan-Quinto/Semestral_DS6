package com.taskflow.app.adapter;

/**
 * Adaptador para la lista de tareas en MainActivity.
 *
 * Parámetros clave:
 * - mostrarAcciones    → true para el Líder (ve Editar/Eliminar), false para Participante
 * - nombresAsignados   → mapa de participanteId → nombre, para mostrar "Asignado a: X" al Líder
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u001aBo\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\u0014\b\u0002\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u0012\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\r\u0012\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\r\u00a2\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\nH\u0016J\u001c\u0010\u0013\u001a\u00020\u000e2\n\u0010\u0014\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0015\u001a\u00020\nH\u0016J\u001c\u0010\u0016\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\nH\u0016R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/taskflow/app/adapter/TareaAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/taskflow/app/adapter/TareaAdapter$ViewHolder;", "lista", "", "Lcom/taskflow/app/model/Tarea;", "mostrarAcciones", "", "nombresAsignados", "", "", "", "onEditar", "Lkotlin/Function1;", "", "onEliminar", "onCompletarToggle", "(Ljava/util/List;ZLjava/util/Map;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getItemCount", "onBindViewHolder", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "ViewHolder", "app_debug"})
public final class TareaAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.taskflow.app.adapter.TareaAdapter.ViewHolder> {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.taskflow.app.model.Tarea> lista = null;
    private final boolean mostrarAcciones = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.Integer, java.lang.String> nombresAsignados = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.taskflow.app.model.Tarea, kotlin.Unit> onEditar = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.taskflow.app.model.Tarea, kotlin.Unit> onEliminar = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.jvm.functions.Function1<com.taskflow.app.model.Tarea, kotlin.Unit> onCompletarToggle = null;
    
    public TareaAdapter(@org.jetbrains.annotations.NotNull()
    java.util.List<com.taskflow.app.model.Tarea> lista, boolean mostrarAcciones, @org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.Integer, java.lang.String> nombresAsignados, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.taskflow.app.model.Tarea, kotlin.Unit> onEditar, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.taskflow.app.model.Tarea, kotlin.Unit> onEliminar, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.taskflow.app.model.Tarea, kotlin.Unit> onCompletarToggle) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.taskflow.app.adapter.TareaAdapter.ViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull()
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    @java.lang.Override()
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull()
    com.taskflow.app.adapter.TareaAdapter.ViewHolder holder, int position) {
    }
    
    @java.lang.Override()
    public int getItemCount() {
        return 0;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/taskflow/app/adapter/TareaAdapter$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "binding", "Lcom/taskflow/app/databinding/ItemTareaBinding;", "(Lcom/taskflow/app/adapter/TareaAdapter;Lcom/taskflow/app/databinding/ItemTareaBinding;)V", "getBinding", "()Lcom/taskflow/app/databinding/ItemTareaBinding;", "app_debug"})
    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @org.jetbrains.annotations.NotNull()
        private final com.taskflow.app.databinding.ItemTareaBinding binding = null;
        
        public ViewHolder(@org.jetbrains.annotations.NotNull()
        com.taskflow.app.databinding.ItemTareaBinding binding) {
            super(null);
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.taskflow.app.databinding.ItemTareaBinding getBinding() {
            return null;
        }
    }
}