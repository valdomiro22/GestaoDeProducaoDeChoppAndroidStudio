package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.repository

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.mapper.toDto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.mapper.toEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.remotedatasource.ProdutoRemoteDataSource
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.repository.ProdutoRepository
import javax.inject.Inject

class ProdutoRepositoryImpl @Inject constructor(
    private val dataSource: ProdutoRemoteDataSource
) : ProdutoRepository {

    override suspend fun insertProduto(produto: ProdutoEntity): Result<Unit> {
        return try {
            val dto = produto.toDto()
            dataSource.insertProduto(dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProduto(
        id: String,
        produto: ProdutoEntity
    ): Result<Unit> {
        return try {
            val dto = produto.toDto()
            dataSource.updateProduto(id, dto)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getProduto(id: String): Result<ProdutoEntity?> {
        return try {
            val dto = dataSource.getProduto(id)
            val entity = dto?.toEntity()
            Result.success(entity)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteProduto(id: String): Result<Unit> {
        return try {
            dataSource.deleteProduto(id)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getAllProdutos(): Result<List<ProdutoEntity>> {
        return try {
            val dtos = dataSource.getAllProdutos()
            val entities = dtos.map { it.toEntity() }
            Result.success(entities)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}