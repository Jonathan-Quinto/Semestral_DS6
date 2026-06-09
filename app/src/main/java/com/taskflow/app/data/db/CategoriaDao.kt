package com.taskflow.app.data.db

import androidx.room.*
import com.taskflow.app.model.Categoria
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriaDao {

    @Query("SELECT * FROM categorias ORDER BY nombre ASC")
    fun obtenerTodas(): Flow<List<Categoria>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertar(categoria: Categoria)

    @Delete
    suspend fun eliminar(categoria: Categoria)
}
