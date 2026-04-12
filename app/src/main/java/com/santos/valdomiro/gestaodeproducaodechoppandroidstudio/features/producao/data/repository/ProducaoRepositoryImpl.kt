package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.repository

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.mapper.toEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.mapper.toDto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.mapper.toEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.remotedatasource.ProducaoRemoteDatasource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.repository.ProducaoRepository
import javax.inject.Inject

class ProducaoRepositoryImpl @Inject constructor(
    private val datasource: ProducaoRemoteDatasource
) : ProducaoRepository {

    override suspend fun insertProducao(producao: ProducaoEntity): Result<Unit> {
        return  try {
            val dto = producao.toDto()
            datasource.insertProducao(dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProducao(id: String, producao: ProducaoEntity): Result<Unit> {
        return  try {
            val dto = producao.toDto()
            datasource.updateProducao(id = id, producao = dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProducao(id: String): Result<ProducaoEntity?> {
        return try {
            val dto = datasource.getProducao(id)
            val entity = dto?.toEntity()
            Result.success(entity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProducao(id: String): Result<Unit> {
        return try {
            datasource.deleteProducao(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllProducoes(): Result<List<ProducaoEntity>> {
        return try {
            val dtos = datasource.getAllProducoes()
            val entities = dtos.map { it.toEntity() }
            Result.success(entities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}