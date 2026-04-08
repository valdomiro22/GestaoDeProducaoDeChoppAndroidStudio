package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.repository

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity


interface ProdutoRepository {
    suspend fun insertProduto(produto: ProdutoEntity): Result<Unit>
    suspend fun updateProduto(id: String, produto: ProdutoEntity): Result<Unit>
    suspend fun getProduto(id: String): Result<ProdutoEntity?>
    suspend fun deleteProduto(id: String): Result<Unit>
    suspend fun getAllProdutos(): Result<List<ProdutoEntity>>
}