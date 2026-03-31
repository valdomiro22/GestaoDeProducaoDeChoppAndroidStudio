package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.data.datasource

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.data.dto.BarrilDto

interface BarrilRemoteDataSource {
    suspend fun insertBarril(barril: BarrilDto)
    suspend fun updateBarril(id: String, barril: BarrilDto)
    suspend fun getBarril(id: String): BarrilDto?
    suspend fun deleteBarril(id: String)
    suspend fun getAllBarris(): List<BarrilDto>
}