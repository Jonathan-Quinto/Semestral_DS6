package com.taskflow.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.taskflow.app.model.Categoria
import com.taskflow.app.model.Tarea
import com.taskflow.app.model.Usuario

/**
 * Base de datos Room de TaskFlow.
 * Singleton — una sola instancia en toda la app.
 *
 * v1 → v2: Se agregan tablas 'usuarios' y 'categorias',
 *           y nuevas columnas en 'tareas' para el sistema de roles.
 *
 * IMPORTANTE: getInstance() fue eliminado — usar siempre obtenerInstancia().
 */
@Database(
    entities = [Tarea::class, Usuario::class, Categoria::class],
    version = 2,
    exportSchema = false
)
abstract class TaskFlowDatabase : RoomDatabase() {

    abstract fun tareaDao(): TareaDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun categoriaDao(): CategoriaDao

    companion object {
        @Volatile
        private var INSTANCE: TaskFlowDatabase? = null

        /**
         * Migración 1→2:
         * - Crea tabla 'usuarios'
         * - Crea tabla 'categorias'
         * - Agrega columnas nuevas a 'tareas' (creadaPor, asignadoA, fechaLimite)
         *
         * Los usuarios existentes antes de la migración no tienen creadaPor,
         * por eso el default es 0 (permite que las tareas viejas sigan funcionando).
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {

                // Nueva tabla: usuarios
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS usuarios (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        nombre TEXT NOT NULL,
                        email TEXT NOT NULL,
                        passwordHash TEXT NOT NULL,
                        rol TEXT NOT NULL,
                        fechaRegistro TEXT NOT NULL DEFAULT ''
                    )
                """)
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_usuarios_email ON usuarios(email)")

                // Nueva tabla: categorias (estaba definida pero faltaba en la DB)
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS categorias (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        nombre TEXT NOT NULL
                    )
                """)

                // Nuevas columnas en tareas para el sistema de roles
                db.execSQL("ALTER TABLE tareas ADD COLUMN fechaLimite TEXT NOT NULL DEFAULT ''")
                db.execSQL("ALTER TABLE tareas ADD COLUMN creadaPor INTEGER NOT NULL DEFAULT 0")
                db.execSQL("ALTER TABLE tareas ADD COLUMN asignadoA INTEGER")

                // Índices para las foreign keys nuevas
                db.execSQL("CREATE INDEX IF NOT EXISTS index_tareas_creadaPor ON tareas(creadaPor)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_tareas_asignadoA ON tareas(asignadoA)")
            }
        }

        fun obtenerInstancia(context: Context): TaskFlowDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    TaskFlowDatabase::class.java,
                    "taskflow_db"
                )
                    // MIGRATION_1_2 se quitó: el ALTER TABLE manual no puede crear las
                    // Foreign Keys que ahora exige la entidad Tarea, y eso hacía que Room
                    // rechazara el esquema migrado y cerrara la app justo después de registrar
                    // un usuario (primer acceso real a la BD).
                    // fallbackToDestructiveMigration() reconstruye la BD desde cero con el
                    // esquema correcto en cualquier cambio de versión — perfecto para este caso
                    // porque no hay datos de producción que conservar.
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instancia
                instancia
            }
        }
    }
}