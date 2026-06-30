package com.taskflow.app.data.db;

/**
 * Base de datos Room de TaskFlow.
 * Singleton — una sola instancia en toda la app.
 *
 * v1 → v2: Se agregan tablas 'usuarios' y 'categorias',
 *          y nuevas columnas en 'tareas' para el sistema de roles.
 *
 * IMPORTANTE: getInstance() fue eliminado — usar siempre obtenerInstancia().
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\n"}, d2 = {"Lcom/taskflow/app/data/db/TaskFlowDatabase;", "Landroidx/room/RoomDatabase;", "()V", "categoriaDao", "Lcom/taskflow/app/data/db/CategoriaDao;", "tareaDao", "Lcom/taskflow/app/data/db/TareaDao;", "usuarioDao", "Lcom/taskflow/app/data/db/UsuarioDao;", "Companion", "app_debug"})
@androidx.room.Database(entities = {com.taskflow.app.model.Tarea.class, com.taskflow.app.model.Usuario.class, com.taskflow.app.model.Categoria.class}, version = 2, exportSchema = false)
public abstract class TaskFlowDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.taskflow.app.data.db.TaskFlowDatabase INSTANCE;
    
    /**
     * Migración 1→2:
     * - Crea tabla 'usuarios'
     * - Crea tabla 'categorias'
     * - Agrega columnas nuevas a 'tareas' (creadaPor, asignadoA, fechaLimite)
     *
     * Los usuarios existentes antes de la migración no tienen creadaPor,
     * por eso el default es 0 (permite que las tareas viejas sigan funcionando).
     */
    @org.jetbrains.annotations.NotNull()
    private static final androidx.room.migration.Migration MIGRATION_1_2 = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.taskflow.app.data.db.TaskFlowDatabase.Companion Companion = null;
    
    public TaskFlowDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.taskflow.app.data.db.TareaDao tareaDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.taskflow.app.data.db.UsuarioDao usuarioDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.taskflow.app.data.db.CategoriaDao categoriaDao();
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/taskflow/app/data/db/TaskFlowDatabase$Companion;", "", "()V", "INSTANCE", "Lcom/taskflow/app/data/db/TaskFlowDatabase;", "MIGRATION_1_2", "Landroidx/room/migration/Migration;", "obtenerInstancia", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.taskflow.app.data.db.TaskFlowDatabase obtenerInstancia(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}