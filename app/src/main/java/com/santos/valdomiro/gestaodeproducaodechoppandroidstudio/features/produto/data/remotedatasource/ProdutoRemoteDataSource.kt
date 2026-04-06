package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.remotedatasource

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.dto.ProdutoDto

interface ProdutoRemoteDataSource {
    suspend fun insertProduto(produto: ProdutoDto)
    suspend fun updateProduto(id: String, produto: ProdutoDto)
    suspend fun getProduto(id: String): ProdutoDto?
    suspend fun deleteProduto(id: String)
    suspend fun getAllProdutos(): List<ProdutoDto>
}