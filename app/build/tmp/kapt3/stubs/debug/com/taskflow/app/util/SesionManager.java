package com.taskflow.app.util;

/**
 * ─────────────────────────────────────────────
 * SesionManager.kt
 * Responsable: Jonathan Quinto
 * Propósito: Gestionar la sesión del usuario activo
 * y el hashing de contraseñas.
 * ─────────────────────────────────────────────
 *
 * Guarda en SharedPreferences:
 *  - ID del usuario logueado
 *  - Nombre
 *  - Email
 *  - Rol ("LIDER" o "PARTICIPANTE")
 *
 * La sesión persiste entre reinicios de la app.
 * Para cerrar sesión se llama a cerrarSesion().
 *
 * Uso desde cualquier Activity:
 *     val sesion = SesionManager(this)
 *     val rol = sesion.getRol()          // "LIDER" o "PARTICIPANTE"
 *     val id  = sesion.getUsuarioId()    // Int
 *     val esLider = sesion.esLider()     // Boolean
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0007\u001a\u00020\bJ\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\nJ\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rJ\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0012\u001a\u00020\b2\u0006\u0010\u0013\u001a\u00020\u0014J\u0006\u0010\u0015\u001a\u00020\nR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/taskflow/app/util/SesionManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "prefs", "Landroid/content/SharedPreferences;", "cerrarSesion", "", "esLider", "", "esParticipante", "getEmail", "", "getNombre", "getRol", "getUsuarioId", "", "guardarSesion", "usuario", "Lcom/taskflow/app/model/Usuario;", "haySesion", "Companion", "app_debug"})
public final class SesionManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String PREFS_NAME = "taskflow_sesion";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ID = "usuario_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_NOMBRE = "usuario_nombre";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_EMAIL = "usuario_email";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_ROL = "usuario_rol";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROL_LIDER = "LIDER";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String ROL_PARTICIPANTE = "PARTICIPANTE";
    @org.jetbrains.annotations.NotNull()
    public static final com.taskflow.app.util.SesionManager.Companion Companion = null;
    
    public SesionManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    /**
     * Guarda los datos del usuario que acaba de iniciar sesión.
     * Se llama justo después de que el login es exitoso en el DAO.
     */
    public final void guardarSesion(@org.jetbrains.annotations.NotNull()
    com.taskflow.app.model.Usuario usuario) {
    }
    
    /**
     * ID del usuario logueado. Devuelve -1 si no hay sesión activa.
     */
    public final int getUsuarioId() {
        return 0;
    }
    
    /**
     * Nombre del usuario logueado.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getNombre() {
        return null;
    }
    
    /**
     * Email del usuario logueado.
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEmail() {
        return null;
    }
    
    /**
     * Rol del usuario logueado: "LIDER" o "PARTICIPANTE".
     */
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getRol() {
        return null;
    }
    
    /**
     * true si el usuario activo es Líder.
     */
    public final boolean esLider() {
        return false;
    }
    
    /**
     * true si el usuario activo es Participante.
     */
    public final boolean esParticipante() {
        return false;
    }
    
    /**
     * true si hay una sesión activa (usuario logueado).
     */
    public final boolean haySesion() {
        return false;
    }
    
    /**
     * Limpia todos los datos de sesión.
     * Después de esto haySesion() devuelve false
     * y SplashActivity redirige al Login.
     */
    public final void cerrarSesion() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/taskflow/app/util/SesionManager$Companion;", "", "()V", "KEY_EMAIL", "", "KEY_ID", "KEY_NOMBRE", "KEY_ROL", "PREFS_NAME", "ROL_LIDER", "ROL_PARTICIPANTE", "hashPassword", "password", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        /**
         * Hashea una contraseña con SHA-256.
         *
         * ¿Por qué SHA-256 y no BCrypt?
         * BCrypt requiere una librería externa. SHA-256 está disponible en Android
         * sin dependencias adicionales y es suficiente para un proyecto universitario.
         * En producción real se usaría BCrypt o Argon2.
         *
         * @param password Contraseña en texto plano
         * @return Hash en hexadecimal de 64 caracteres
         */
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String hashPassword(@org.jetbrains.annotations.NotNull()
        java.lang.String password) {
            return null;
        }
    }
}