package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.remotedatasource

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.dto.ProducaoRemoteDto

interface ProducaoRemoteDatasource {
    suspend fun insertProducao(producao: ProducaoRemoteDto)
    suspend fun updateProducao(id: String, producao: ProducaoRemoteDto)
    suspend fun getProducao(id: String): ProducaoRemoteDto?
    suspend fun deleteProducao(id: String)
    suspend fun getAllProducoes(): List<ProducaoRemoteDto>
    suspend fun getAllProducoesDaGrade(gradeId: String): List<ProducaoRemoteDto>
}