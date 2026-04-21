package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.data.repository

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.data.mapper.toDto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.data.mapper.toEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.data.remotedatasource.QuantidadeHorariaRemoteDatasource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.QuantidadeHorariaEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.repository.QuantidadeHorariaRepository
import javax.inject.Inject

class QuantidadeHorariaRepositoryImpl @Inject constructor(
    private val datasource: QuantidadeHorariaRemoteDatasource
) : QuantidadeHorariaRepository {

    override suspend fun insertQtHoraria(qtHoraria: QuantidadeHorariaEntity): Result<Unit> {
        return  try {
            val dto = qtHoraria.toDto()
            datasource.insertQtHoraria(dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateQtHoraria(id: String, qtHoraria: QuantidadeHorariaEntity): Result<Unit> {
        return  try {
            val dto = qtHoraria.toDto()
            datasource.updateQtHoraria(id = id, qtHoraria = dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getQtHoraria(id: String): Result<QuantidadeHorariaEntity?> {
        return try {
            val dto = datasource.getQtHoraria(id)
            val entity = dto?.toEntity()
            Result.success(entity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteQtHoraria(id: String): Result<Unit> {
        return try {
            datasource.deleteQtHoraria(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllQtHorarias(): Result<List<QuantidadeHorariaEntity>> {
        return try {
            val dtos = datasource.getAllQtHorarias()
            val entities = dtos.map { it.toEntity() }
            Result.success(entities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllQtHorariasDaProducao(producaoId: String): Result<List<QuantidadeHorariaEntity>> {
        return try {
            val dtos = datasource.getAllQtHorariasDaProducao(producaoId = producaoId)
            val entities = dtos.map { it.toEntity() }
            Result.success(entities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}