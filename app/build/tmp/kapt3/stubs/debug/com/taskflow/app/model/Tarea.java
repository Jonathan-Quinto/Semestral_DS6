package com.taskflow.app.model;

/**
 * Entidad principal que representa una tarea en la base de datos.
 *
 * Campos nuevos para el sistema de roles:
 * - creadaPor   → ID del usuario (Líder) que creó la tarea
 * - asignadoA   → ID del usuario (Participante) al que se le asigna
 *                 Si es null, la tarea no está asignada a nadie todavía
 * - fechaLimite → Fecha límite opcional en "yyyy-MM-dd"
 *
 * ForeignKeys garantizan integridad referencial con la tabla usuarios.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b%\b\u0087\b\u0018\u00002\u00020\u0001Bi\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u000fJ\t\u0010\u001f\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010 \u001a\u0004\u0018\u00010\u0003H\u00c6\u0003\u00a2\u0006\u0002\u0010\u0011J\t\u0010!\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003J\t\u0010#\u001a\u00020\u0005H\u00c6\u0003J\t\u0010$\u001a\u00020\u0005H\u00c6\u0003J\t\u0010%\u001a\u00020\nH\u00c6\u0003J\t\u0010&\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\'\u001a\u00020\u0005H\u00c6\u0003J\t\u0010(\u001a\u00020\u0003H\u00c6\u0003Jt\u0010)\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\n2\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010*J\u0013\u0010+\u001a\u00020\n2\b\u0010,\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010-\u001a\u00020\u0003H\u00d6\u0001J\t\u0010.\u001a\u00020\u0005H\u00d6\u0001R\u0015\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\n\n\u0002\u0010\u0012\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\r\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0014R\u0011\u0010\u000b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0014R\u0011\u0010\f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0014R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0018R\u0011\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0014R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0014\u00a8\u0006/"}, d2 = {"Lcom/taskflow/app/model/Tarea;", "", "id", "", "titulo", "", "descripcion", "prioridad", "categoria", "completada", "", "fechaCreacion", "fechaLimite", "creadaPor", "asignadoA", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;ILjava/lang/Integer;)V", "getAsignadoA", "()Ljava/lang/Integer;", "Ljava/lang/Integer;", "getCategoria", "()Ljava/lang/String;", "getCompletada", "()Z", "getCreadaPor", "()I", "getDescripcion", "getFechaCreacion", "getFechaLimite", "getId", "getPrioridad", "getTitulo", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZLjava/lang/String;Ljava/lang/String;ILjava/lang/Integer;)Lcom/taskflow/app/model/Tarea;", "equals", "other", "hashCode", "toString", "app_debug"})
@androidx.room.Entity(tableName = "tareas", foreignKeys = {@androidx.room.ForeignKey(entity = com.taskflow.app.model.Usuario.class, parentColumns = {"id"}, childColumns = {"creadaPor"}, onDelete = 5), @androidx.room.ForeignKey(entity = com.taskflow.app.model.Usuario.class, parentColumns = {"id"}, childColumns = {"asignadoA"}, onDelete = 3)}, indices = {@androidx.room.Index(value = {"creadaPor"}), @androidx.room.Index(value = {"asignadoA"})})
public final class Tarea {
    @androidx.room.PrimaryKey(autoGenerate = true)
    private final int id = 0;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String titulo = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String descripcion = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String prioridad = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String categoria = null;
    private final boolean completada = false;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String fechaCreacion = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String fechaLimite = null;
    private final int creadaPor = 0;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.Integer asignadoA = null;
    
    public Tarea(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String titulo, @org.jetbrains.annotations.NotNull()
    java.lang.String descripcion, @org.jetbrains.annotations.NotNull()
    java.lang.String prioridad, @org.jetbrains.annotations.NotNull()
    java.lang.String categoria, boolean completada, @org.jetbrains.annotations.NotNull()
    java.lang.String fechaCreacion, @org.jetbrains.annotations.NotNull()
    java.lang.String fechaLimite, int creadaPor, @org.jetbrains.annotations.Nullable()
    java.lang.Integer asignadoA) {
        super();
    }
    
    public final int getId() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getTitulo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDescripcion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPrioridad() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCategoria() {
        return null;
    }
    
    public final boolean getCompletada() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFechaCreacion() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getFechaLimite() {
        return null;
    }
    
    public final int getCreadaPor() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer getAsignadoA() {
        return null;
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Integer component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    public final int component9() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.taskflow.app.model.Tarea copy(int id, @org.jetbrains.annotations.NotNull()
    java.lang.String titulo, @org.jetbrains.annotations.NotNull()
    java.lang.String descripcion, @org.jetbrains.annotations.NotNull()
    java.lang.String prioridad, @org.jetbrains.annotations.NotNull()
    java.lang.String categoria, boolean completada, @org.jetbrains.annotations.NotNull()
    java.lang.String fechaCreacion, @org.jetbrains.annotations.NotNull()
    java.lang.String fechaLimite, int creadaPor, @org.jetbrains.annotations.Nullable()
    java.lang.Integer asignadoA) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}