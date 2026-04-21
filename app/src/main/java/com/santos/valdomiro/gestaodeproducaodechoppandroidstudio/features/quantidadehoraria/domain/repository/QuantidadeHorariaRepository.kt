package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.repository

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.QuantidadeHorariaEntity

interface QuantidadeHorariaRepository {
    suspend fun insertQtHoraria(qtHoraria: QuantidadeHorariaEntity): Result<Unit>
    suspend fun updateQtHoraria(id: String, qtHoraria: QuantidadeHorariaEntity): Result<Unit>
    suspend fun getQtHoraria(id: String): Result<QuantidadeHorariaEntity?>
    suspend fun deleteQtHoraria(id: String): Result<Unit>
    suspend fun getAllQtHorarias(): Result<List<QuantidadeHorariaEntity>>
    suspend fun getAllQtHorariasDaProducao(producaoId: String): Result<List<QuantidadeHorariaEntity>>
}