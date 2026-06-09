package com.taskflow.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.taskflow.app.model.Tarea

/**
 * Base de datos Room de TaskFlow.
 * Singleton — una sola instancia en toda la app.
 */
@Database(entities = [Tarea::class], version = 1, exportSchema = false)
abstract class TaskFlowDatabase : RoomDatabase() {

    abstract fun tareaDao(): TareaDao

    companion object {
        @Volatile
        private var INSTANCE: TaskFlowDatabase? = null

        fun obtenerInstancia(context: Context): TaskFlowDatabase {
            return INSTANCE ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    TaskFlowDatabase::class.java,
                    "taskflow_db"
                ).build()
                INSTANCE = instancia
                instancia
            }
        }
    }
}
