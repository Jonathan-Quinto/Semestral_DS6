package com.taskflow.app.data.repository;

/**
 * ─────────────────────────────────────────────
 * TareaRepository.kt
 * Responsable: Jonathan Quinto
 * Propósito: Centralizar la lógica de negocio
 * de tareas con conciencia del rol activo.
 * ─────────────────────────────────────────────
 *
 * El repositorio recibe el SesionManager y decide
 * automáticamente qué query usar según el rol:
 *
 *  Líder       → ve todas las tareas que creó
 *  Participante → ve solo las que le asignaron
 *
 * Las Activities solo llaman a este repositorio,
 * sin preocuparse por cuál query usar.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u001e\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u001a\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00150\u00142\u0006\u0010\u0016\u001a\u00020\u0017J\u000e\u0010\u0018\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001a\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u000e\u0010\u001b\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0019JD\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u00172\u0006\u0010\u001f\u001a\u00020\u00172\u0006\u0010 \u001a\u00020\u00172\b\b\u0002\u0010!\u001a\u00020\u00172\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010\u0010H\u0086@\u00a2\u0006\u0002\u0010#J\u0016\u0010$\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010%J\u0016\u0010&\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u0014\u0010\'\u001a\b\u0012\u0004\u0012\u00020(0\u0015H\u0086@\u00a2\u0006\u0002\u0010\u0019J\u001a\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00150\u00142\u0006\u0010 \u001a\u00020\u0017J\u0018\u0010*\u001a\u0004\u0018\u00010\f2\u0006\u0010+\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010%J\u0012\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00150\u0014J\u0012\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u00150\u0014J\u0016\u0010.\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006/"}, d2 = {"Lcom/taskflow/app/data/repository/TareaRepository;", "", "tareaDao", "Lcom/taskflow/app/data/db/TareaDao;", "usuarioDao", "Lcom/taskflow/app/data/db/UsuarioDao;", "sesionManager", "Lcom/taskflow/app/util/SesionManager;", "(Lcom/taskflow/app/data/db/TareaDao;Lcom/taskflow/app/data/db/UsuarioDao;Lcom/taskflow/app/util/SesionManager;)V", "actualizarTarea", "", "tarea", "Lcom/taskflow/app/model/Tarea;", "(Lcom/taskflow/app/model/Tarea;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "asignarTarea", "tareaId", "", "participanteId", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "buscar", "Lkotlinx/coroutines/flow/Flow;", "", "texto", "", "contarCompletadas", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "contarSinAsignar", "contarTotal", "crearTarea", "titulo", "descripcion", "prioridad", "categoria", "fechaLimite", "asignadoA", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "desasignarTarea", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "eliminarTarea", "obtenerParticipantes", "Lcom/taskflow/app/model/Usuario;", "obtenerPorCategoria", "obtenerPorId", "id", "obtenerSinAsignar", "obtenerTareasPorRol", "toggleCompletada", "app_debug"})
public final class TareaRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.taskflow.app.data.db.TareaDao tareaDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.taskflow.app.data.db.UsuarioDao usuarioDao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.taskflow.app.util.SesionManager sesionManager = null;
    
    public TareaRepository(@org.jetbrains.annotations.NotNull()
    com.taskflow.app.data.db.TareaDao tareaDao, @org.jetbrains.annotations.NotNull()
    com.taskflow.app.data.db.UsuarioDao usuarioDao, @org.jetbrains.annotations.NotNull()
    com.taskflow.app.util.SesionManager sesionManager) {
        super();
    }
    
    /**
     * Devuelve el Flow de tareas según el rol del usuario activo.
     * La Activity observa esto sin saber si es Líder o Participante.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Tarea>> obtenerTareasPorRol() {
        return null;
    }
    
    /**
     * Tareas sin asignar del Líder activo.
     * Solo tiene sentido llamarlo cuando esLider() == true.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Tarea>> obtenerSinAsignar() {
        return null;
    }
    
    /**
     * Búsqueda por texto con conciencia de rol.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Tarea>> buscar(@org.jetbrains.annotations.NotNull()
    java.lang.String texto) {
        return null;
    }
    
    /**
     * Tareas filtradas por categoría con conciencia de rol.
     */
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Tarea>> obtenerPorCategoria(@org.jetbrains.annotations.NotNull()
    java.lang.String categoria) {
        return null;
    }
    
    /**
     * Crea una nueva tarea asignando automáticamente el Líder activo como creador.
     * Solo el Líder puede crear tareas.
     *
     * @return true si se insertó correctamente
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object crearTarea(@org.jetbrains.annotations.NotNull()
    java.lang.String titulo, @org.jetbrains.annotations.NotNull()
    java.lang.String descripcion, @org.jetbrains.annotations.NotNull()
    java.lang.String prioridad, @org.jetbrains.annotations.NotNull()
    java.lang.String categoria, @org.jetbrains.annotations.NotNull()
    java.lang.String fechaLimite, @org.jetbrains.annotations.Nullable()
    java.lang.Integer asignadoA, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object actualizarTarea(@org.jetbrains.annotations.NotNull()
    com.taskflow.app.model.Tarea tarea, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object eliminarTarea(@org.jetbrains.annotations.NotNull()
    com.taskflow.app.model.Tarea tarea, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Cambia el estado completada/pendiente de una tarea.
     * El Participante puede hacerlo en sus propias tareas.
     * El Líder puede hacerlo en cualquier tarea que creó.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object toggleCompletada(@org.jetbrains.annotations.NotNull()
    com.taskflow.app.model.Tarea tarea, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Asigna una tarea a un participante.
     * Verifica internamente que el usuario activo sea Líder.
     *
     * @return true si se asignó, false si no es Líder o falló la BD
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object asignarTarea(int tareaId, int participanteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Quita la asignación de una tarea (vuelve a "sin asignar").
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object desasignarTarea(int tareaId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * Devuelve todos los participantes disponibles para asignar.
     * El Líder usa esta lista en el formulario de creación/edición.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object obtenerParticipantes(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.taskflow.app.model.Usuario>> $completion) {
        return null;
    }
    
    /**
     * Conteo de tareas totales según el rol activo.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object contarTotal(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    /**
     * Conteo de tareas completadas según el rol activo.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object contarCompletadas(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    /**
     * Tareas sin asignar del Líder activo (solo útil para el Líder).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object contarSinAsignar(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object obtenerPorId(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.taskflow.app.model.Tarea> $completion) {
        return null;
    }
}