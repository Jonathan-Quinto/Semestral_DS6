package com.taskflow.app.data.api

import com.taskflow.app.model.ClimaRespuesta
import retrofit2.http.GET
import retrofit2.http.Query

interface ClimaApi {
    @GET("weather")
    suspend fun obtenerClima(
        @Query("q") ciudad: String,
        @Query("appid") apiKey: String,
        @Query("units") unidades: String = "metric",
        @Query("lang") idioma: String = "es"
    ): ClimaRespuesta
}
