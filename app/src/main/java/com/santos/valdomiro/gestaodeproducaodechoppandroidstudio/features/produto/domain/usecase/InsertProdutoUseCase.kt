package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.repository.ProdutoRepository
import java.util.UUID
import javax.inject.Inject

class InsertProdutoUseCase @Inject constructor(
    private val repository: ProdutoRepository
) {
    suspend operator fun invoke(produto: ProdutoEntity): Result<Unit> {
        val idGerado = UUID.randomUUID().toString()
        val produtoComId = produto.copy(id = idGerado)
        return repository.insertProduto(produtoComId)
    }
}