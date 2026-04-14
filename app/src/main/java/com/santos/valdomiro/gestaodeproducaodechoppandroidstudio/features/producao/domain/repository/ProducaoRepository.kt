package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.repository

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity

interface ProducaoRepository {
    suspend fun insertProducao(producao: ProducaoEntity): Result<Unit>
    suspend fun updateProducao(id: String, producao: ProducaoEntity): Result<Unit>
    suspend fun getProducao(id: String): Result<ProducaoEntity?>
    suspend fun deleteProducao(id: String): Result<Unit>
    suspend fun getAllProducoes(): Result<List<ProducaoEntity>>
    suspend fun getAllProducoesDaGrade(gradeId: String): Result<List<ProducaoEntity>>
}