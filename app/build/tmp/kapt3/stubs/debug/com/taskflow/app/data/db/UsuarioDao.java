package com.taskflow.app.data.db;

/**
 * DAO de usuarios — operaciones de base de datos para el sistema de autenticación y roles.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0006\bg\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\u00052\u0006\u0010\n\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u000bJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0010J \u0010\u0011\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\n\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u0007H\u00a7@\u00a2\u0006\u0002\u0010\u0013J\u0014\u0010\u0014\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00160\u0015H\'J\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0016H\u00a7@\u00a2\u0006\u0002\u0010\u0018J\u0018\u0010\u0019\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u001aJ\u0014\u0010\u001b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u00160\u0015H\'\u00a8\u0006\u001c"}, d2 = {"Lcom/taskflow/app/data/db/UsuarioDao;", "", "actualizarNombre", "", "id", "", "nombre", "", "(ILjava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "existeEmail", "email", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertar", "", "usuario", "Lcom/taskflow/app/model/Usuario;", "(Lcom/taskflow/app/model/Usuario;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "login", "passwordHash", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "obtenerParticipantes", "Lkotlinx/coroutines/flow/Flow;", "", "obtenerParticipantesSuspend", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "obtenerPorId", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "obtenerTodos", "app_debug"})
@androidx.room.Dao()
public abstract interface UsuarioDao {
    
    /**
     * Inserta un nuevo usuario.
     * Devuelve el ID generado, o -1 si el email ya existe (IGNORE).
     */
    @androidx.room.Insert(onConflict = 5)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertar(@org.jetbrains.annotations.NotNull()
    com.taskflow.app.model.Usuario usuario, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Long> $completion);
    
    /**
     * Busca un usuario por email y contraseña hasheada.
     * Usado en el login — si devuelve null, las credenciales son incorrectas.
     */
    @androidx.room.Query(value = "SELECT * FROM usuarios WHERE email = :email AND passwordHash = :passwordHash LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String passwordHash, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.taskflow.app.model.Usuario> $completion);
    
    /**
     * Verifica si un email ya está registrado.
     * Usado en el registro para mostrar error antes de intentar insertar.
     */
    @androidx.room.Query(value = "SELECT COUNT(*) FROM usuarios WHERE email = :email")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object existeEmail(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Obtiene todos los participantes disponibles para asignar tareas.
     * Solo el Líder necesita esta lista (para el spinner de asignación).
     */
    @androidx.room.Query(value = "SELECT * FROM usuarios WHERE rol = \'PARTICIPANTE\' ORDER BY nombre ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Usuario>> obtenerParticipantes();
    
    /**
     * Versión suspend de obtenerParticipantes — para cargar una sola vez (spinner).
     */
    @androidx.room.Query(value = "SELECT * FROM usuarios WHERE rol = \'PARTICIPANTE\' ORDER BY nombre ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerParticipantesSuspend(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.taskflow.app.model.Usuario>> $completion);
    
    /**
     * Obtiene todos los usuarios (para pantalla de administración del Líder).
     */
    @androidx.room.Query(value = "SELECT * FROM usuarios ORDER BY rol ASC, nombre ASC")
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.taskflow.app.model.Usuario>> obtenerTodos();
    
    /**
     * Obtiene un usuario por su ID — para mostrar el perfil activo.
     */
    @androidx.room.Query(value = "SELECT * FROM usuarios WHERE id = :id LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object obtenerPorId(int id, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.taskflow.app.model.Usuario> $completion);
    
    /**
     * Actualiza los datos de perfil de un usuario (nombre).
     * El email y rol no se cambian desde aquí por seguridad.
     */
    @androidx.room.Query(value = "UPDATE usuarios SET nombre = :nombre WHERE id = :id")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object actualizarNombre(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String nombre, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}