package com.taskflow.app.data.db;

/**
 * DAO de tareas — define todas las operaciones de base de datos disponibles.
 *
 * Queries nuevas para el sistema de roles:
 * - obtenerPorLider()               → el Líder ve TODAS las tareas que creó
 * - obtenerPorParticipante()         → el Participante ve SOLO las que le asignaron
 * - obtenerSinAsignar()             → tareas creadas pero sin participante asignado
 * - asignarParticipante()           → el Líder asigna una tarea a un participante
 * - contarPorCategoriaYParticipante → FIX: conteo para categorías del participante
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001c\bg\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u000bJ$\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\r2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\'J$\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011H\'J\u000e\u0010\u0013\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u0016\u0010\u0015\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0017\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\u0018\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u001e\u0010\u001b\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u001e\u0010\u001d\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u0011H\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u0016\u0010\u001e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u000e\u0010\u001f\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u0016\u0010 \u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010!\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010\"\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u0016\u0010#\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010$\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J$\u0010%\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\r2\u0006\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u0011H\'J$\u0010&\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\r2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u0019\u001a\u00020\u0011H\'J\u0018\u0010\'\u001a\u0004\u0018\u00010\u00052\u0006\u0010(\u001a\u00020\tH\u00a7@\u00a2\u0006\u0002\u0010\u0016J\u001c\u0010)\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\r2\u0006\u0010\u000f\u001a\u00020\tH\'J\u001c\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\r2\u0006\u0010\n\u001a\u00020\tH\'J\u001c\u0010+\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\r2\u0006\u0010\u000f\u001a\u00020\tH\'J\u0014\u0010,\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000e0\rH\'\u00a8\u0006-"}, d2 = {"Lcom/taskflow/app/data/db/TareaDao;", "", "actualizar", "", "tarea", "Lcom/taskflow/app/model/Tarea;", "(Lcom/taskflow/app/model/Tarea;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "asignarParticipante", "tareaId", "", "participanteId", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "buscarPorLider", "Lkotlinx/coroutines/flow/Flow;", "", "liderId", "texto", "", "buscarPorParticipante", "contarCompletadas", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "contarCompletadasPorLider", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "contarCompletadasPorParticipante", "contarPorCategoria", "categoria", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "contarPorCategoriaYLider", "(ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "contarPorCategoriaYParticipante", "contarSinAsignarPorLider", "contarTotal", "contarTotalPorLider", "contarTotalPorParticipante", "desasignar", "eliminar", "insertar", "obtenerPorCategoriaYLider", "obtenerPorCategoriaYParticipante", "obtenerPorId", "id", "obtenerPorLider", "obtenerPorParticipante", "obtenerSinAsignar", "obtenerTodas", "app_debug"})
@androidx.room.Dao()
public abstract interface TareaDao {
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertar(@org.jetbrains.annotations.NotNull()
    com.taskflow.app.model.Tarea tarea, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Update()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object actualizar(@org.jetbrains.annotations.NotNull()
    com.taskflow.app.model.Tarea tarea, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Delete()
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object eliminar(@org.jetbrains.annotations.NotNull()
    com.taskflow.app.model.Tarea tarea, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM tareas WHERE id = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerPorId(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.taskflow.app.model.Tarea> $completion);
    
    /**
     * Para el Líder: todas las tareas que él creó, ordenadas por estado y prioridad.
     */
    @androidx.room.Query(value = "\n        SELECT * FROM tareas \n        WHERE creadaPor = :liderId\n        ORDER BY completada ASC,\n        CASE prioridad WHEN \'Alta\' THEN 1 WHEN \'Media\' THEN 2 ELSE 3 END ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Tarea>> obtenerPorLider(int liderId);
    
    /**
     * Para el Participante: solo las tareas que le fueron asignadas.
     */
    @androidx.room.Query(value = "\n        SELECT * FROM tareas \n        WHERE asignadoA = :participanteId\n        ORDER BY completada ASC,\n        CASE prioridad WHEN \'Alta\' THEN 1 WHEN \'Media\' THEN 2 ELSE 3 END ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Tarea>> obtenerPorParticipante(int participanteId);
    
    /**
     * Tareas creadas por el Líder que aún no tienen participante asignado.
     */
    @androidx.room.Query(value = "\n        SELECT * FROM tareas \n        WHERE creadaPor = :liderId AND asignadoA IS NULL\n        ORDER BY CASE prioridad WHEN \'Alta\' THEN 1 WHEN \'Media\' THEN 2 ELSE 3 END ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Tarea>> obtenerSinAsignar(int liderId);
    
    /**
     * Asigna un participante a una tarea existente.
     */
    @androidx.room.Query(value = "UPDATE tareas SET asignadoA = :participanteId WHERE id = :tareaId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object asignarParticipante(int tareaId, int participanteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Desasigna el participante de una tarea (vuelve a sin asignar).
     */
    @androidx.room.Query(value = "UPDATE tareas SET asignadoA = NULL WHERE id = :tareaId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object desasignar(int tareaId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM tareas \n        WHERE creadaPor = :liderId AND categoria = :categoria\n        ORDER BY completada ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Tarea>> obtenerPorCategoriaYLider(int liderId, @org.jetbrains.annotations.NotNull()
    java.lang.String categoria);
    
    @androidx.room.Query(value = "\n        SELECT * FROM tareas \n        WHERE asignadoA = :participanteId AND categoria = :categoria\n        ORDER BY completada ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Tarea>> obtenerPorCategoriaYParticipante(int participanteId, @org.jetbrains.annotations.NotNull()
    java.lang.String categoria);
    
    @androidx.room.Query(value = "\n        SELECT * FROM tareas \n        WHERE creadaPor = :liderId AND titulo LIKE \'%\' || :texto || \'%\'\n        ORDER BY completada ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Tarea>> buscarPorLider(int liderId, @org.jetbrains.annotations.NotNull()
    java.lang.String texto);
    
    @androidx.room.Query(value = "\n        SELECT * FROM tareas \n        WHERE asignadoA = :participanteId AND titulo LIKE \'%\' || :texto || \'%\'\n        ORDER BY completada ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Tarea>> buscarPorParticipante(int participanteId, @org.jetbrains.annotations.NotNull()
    java.lang.String texto);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tareas WHERE creadaPor = :liderId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object contarTotalPorLider(int liderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tareas WHERE creadaPor = :liderId AND completada = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object contarCompletadasPorLider(int liderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tareas WHERE asignadoA = :participanteId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object contarTotalPorParticipante(int participanteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tareas WHERE asignadoA = :participanteId AND completada = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object contarCompletadasPorParticipante(int participanteId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tareas WHERE creadaPor = :liderId AND categoria = :categoria")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object contarPorCategoriaYLider(int liderId, @org.jetbrains.annotations.NotNull()
    java.lang.String categoria, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * FIX: Método faltante — conteo de categoría para el Participante
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tareas WHERE asignadoA = :participanteId AND categoria = :categoria")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object contarPorCategoriaYParticipante(int participanteId, @org.jetbrains.annotations.NotNull()
    java.lang.String categoria, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tareas WHERE creadaPor = :liderId AND asignadoA IS NULL")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object contarSinAsignarPorLider(int liderId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "\n        SELECT * FROM tareas \n        ORDER BY completada ASC,\n        CASE prioridad WHEN \'Alta\' THEN 1 WHEN \'Media\' THEN 2 ELSE 3 END ASC\n    ")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Tarea>> obtenerTodas();
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tareas")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object contarTotal(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tareas WHERE completada = 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object contarCompletadas(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM tareas WHERE categoria = :categoria")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object contarPorCategoria(@org.jetbrains.annotations.NotNull()
    java.lang.String categoria, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
}