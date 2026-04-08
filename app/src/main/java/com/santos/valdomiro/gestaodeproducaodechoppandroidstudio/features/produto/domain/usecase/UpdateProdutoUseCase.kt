package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.repository.ProdutoRepository
import javax.inject.Inject

class UpdateProdutoUseCase @Inject constructor(
    private val repository: ProdutoRepository
) {
    suspend operator fun invoke(id: String, produto: ProdutoEntity): Result<Unit> {
        return repository.updateProduto(id = id, produto = produto)
    }
}