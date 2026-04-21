package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.data.remotedatasource

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.data.dto.QuantidadeHorariaDto

interface QuantidadeHorariaRemoteDatasource {
    suspend fun insertQtHoraria(qtHoraria: QuantidadeHorariaDto)
    suspend fun updateQtHoraria(id: String, qtHoraria: QuantidadeHorariaDto)
    suspend fun getQtHoraria(id: String): QuantidadeHorariaDto?
    suspend fun deleteQtHoraria(id: String)
    suspend fun getAllQtHorarias(): List<QuantidadeHorariaDto>
    suspend fun getAllQtHorariasDaProducao(producaoId: String): List<QuantidadeHorariaDto>
}