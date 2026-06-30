package com.taskflow.app.data.repository;

/**
 * ─────────────────────────────────────────────
 * AuthRepository.kt
 * Responsable: Jonathan Quinto
 * Propósito: Centralizar toda la lógica de
 * autenticación — registro, login y validaciones.
 * ─────────────────────────────────────────────
 *
 * Las Activities nunca llaman al DAO directamente.
 * Pasan por aquí para:
 *  1. Validar los datos antes de tocar la BD
 *  2. Hashear la contraseña antes de guardar/comparar
 *  3. Devolver un resultado tipado (sealed class)
 *     para que la UI sepa exactamente qué pasó
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0002\u0010\u0011B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\nJ.\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\u000fR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2 = {"Lcom/taskflow/app/data/repository/AuthRepository;", "", "usuarioDao", "Lcom/taskflow/app/data/db/UsuarioDao;", "(Lcom/taskflow/app/data/db/UsuarioDao;)V", "login", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoLogin;", "email", "", "password", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registrar", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoRegistro;", "nombre", "rol", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ResultadoLogin", "ResultadoRegistro", "app_debug"})
public final class AuthRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.taskflow.app.data.db.UsuarioDao usuarioDao = null;
    
    public AuthRepository(@org.jetbrains.annotations.NotNull()
    com.taskflow.app.data.db.UsuarioDao usuarioDao) {
        super();
    }
    
    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * Flujo:
     * 1. Verifica que el email no esté ya registrado
     * 2. Hashea la contraseña con SHA-256
     * 3. Inserta el usuario en la BD
     * 4. Devuelve el resultado tipado para que la UI reaccione
     *
     * @param nombre   Nombre completo del usuario
     * @param email    Email (debe ser único)
     * @param password Contraseña en texto plano (se hashea aquí)
     * @param rol      "LIDER" o "PARTICIPANTE"
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object registrar(@org.jetbrains.annotations.NotNull()
    java.lang.String nombre, @org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    java.lang.String rol, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.taskflow.app.data.repository.AuthRepository.ResultadoRegistro> $completion) {
        return null;
    }
    
    /**
     * Autentica un usuario con email y contraseña.
     *
     * Flujo:
     * 1. Hashea la contraseña ingresada
     * 2. Busca en la BD un usuario con ese email + hash
     * 3. Si existe → Exito; si no → CredencialesInvalidas
     *
     * Nota: El email se normaliza a minúsculas para evitar
     * problemas de "User@Email.com" vs "user@email.com".
     *
     * @param email    Email ingresado en el formulario
     * @param password Contraseña en texto plano
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object login(@org.jetbrains.annotations.NotNull()
    java.lang.String email, @org.jetbrains.annotations.NotNull()
    java.lang.String password, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.taskflow.app.data.repository.AuthRepository.ResultadoLogin> $completion) {
        return null;
    }
    
    /**
     * Resultado del intento de login
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/taskflow/app/data/repository/AuthRepository$ResultadoLogin;", "", "()V", "CredencialesInvalidas", "ErrorBaseDatos", "Exito", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoLogin$CredencialesInvalidas;", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoLogin$ErrorBaseDatos;", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoLogin$Exito;", "app_debug"})
    public static abstract class ResultadoLogin {
        
        private ResultadoLogin() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/taskflow/app/data/repository/AuthRepository$ResultadoLogin$CredencialesInvalidas;", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoLogin;", "()V", "app_debug"})
        public static final class CredencialesInvalidas extends com.taskflow.app.data.repository.AuthRepository.ResultadoLogin {
            @org.jetbrains.annotations.NotNull()
            public static final com.taskflow.app.data.repository.AuthRepository.ResultadoLogin.CredencialesInvalidas INSTANCE = null;
            
            private CredencialesInvalidas() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/taskflow/app/data/repository/AuthRepository$ResultadoLogin$ErrorBaseDatos;", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoLogin;", "()V", "app_debug"})
        public static final class ErrorBaseDatos extends com.taskflow.app.data.repository.AuthRepository.ResultadoLogin {
            @org.jetbrains.annotations.NotNull()
            public static final com.taskflow.app.data.repository.AuthRepository.ResultadoLogin.ErrorBaseDatos INSTANCE = null;
            
            private ErrorBaseDatos() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/taskflow/app/data/repository/AuthRepository$ResultadoLogin$Exito;", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoLogin;", "usuario", "Lcom/taskflow/app/model/Usuario;", "(Lcom/taskflow/app/model/Usuario;)V", "getUsuario", "()Lcom/taskflow/app/model/Usuario;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class Exito extends com.taskflow.app.data.repository.AuthRepository.ResultadoLogin {
            @org.jetbrains.annotations.NotNull()
            private final com.taskflow.app.model.Usuario usuario = null;
            
            public Exito(@org.jetbrains.annotations.NotNull()
            com.taskflow.app.model.Usuario usuario) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.taskflow.app.model.Usuario getUsuario() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.taskflow.app.model.Usuario component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.taskflow.app.data.repository.AuthRepository.ResultadoLogin.Exito copy(@org.jetbrains.annotations.NotNull()
            com.taskflow.app.model.Usuario usuario) {
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
    }
    
    /**
     * Resultado del intento de registro
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b\u00a8\u0006\t"}, d2 = {"Lcom/taskflow/app/data/repository/AuthRepository$ResultadoRegistro;", "", "()V", "EmailYaExiste", "ErrorBaseDatos", "Exito", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoRegistro$EmailYaExiste;", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoRegistro$ErrorBaseDatos;", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoRegistro$Exito;", "app_debug"})
    public static abstract class ResultadoRegistro {
        
        private ResultadoRegistro() {
            super();
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/taskflow/app/data/repository/AuthRepository$ResultadoRegistro$EmailYaExiste;", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoRegistro;", "()V", "app_debug"})
        public static final class EmailYaExiste extends com.taskflow.app.data.repository.AuthRepository.ResultadoRegistro {
            @org.jetbrains.annotations.NotNull()
            public static final com.taskflow.app.data.repository.AuthRepository.ResultadoRegistro.EmailYaExiste INSTANCE = null;
            
            private EmailYaExiste() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/taskflow/app/data/repository/AuthRepository$ResultadoRegistro$ErrorBaseDatos;", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoRegistro;", "()V", "app_debug"})
        public static final class ErrorBaseDatos extends com.taskflow.app.data.repository.AuthRepository.ResultadoRegistro {
            @org.jetbrains.annotations.NotNull()
            public static final com.taskflow.app.data.repository.AuthRepository.ResultadoRegistro.ErrorBaseDatos INSTANCE = null;
            
            private ErrorBaseDatos() {
            }
        }
        
        @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/taskflow/app/data/repository/AuthRepository$ResultadoRegistro$Exito;", "Lcom/taskflow/app/data/repository/AuthRepository$ResultadoRegistro;", "usuario", "Lcom/taskflow/app/model/Usuario;", "(Lcom/taskflow/app/model/Usuario;)V", "getUsuario", "()Lcom/taskflow/app/model/Usuario;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "app_debug"})
        public static final class Exito extends com.taskflow.app.data.repository.AuthRepository.ResultadoRegistro {
            @org.jetbrains.annotations.NotNull()
            private final com.taskflow.app.model.Usuario usuario = null;
            
            public Exito(@org.jetbrains.annotations.NotNull()
            com.taskflow.app.model.Usuario usuario) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.taskflow.app.model.Usuario getUsuario() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.taskflow.app.model.Usuario component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.taskflow.app.data.repository.AuthRepository.ResultadoRegistro.Exito copy(@org.jetbrains.annotations.NotNull()
            com.taskflow.app.model.Usuario usuario) {
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
    }
}