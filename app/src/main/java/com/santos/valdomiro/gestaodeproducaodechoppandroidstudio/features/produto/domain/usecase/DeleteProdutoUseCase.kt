package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.repository.ProdutoRepository
import javax.inject.Inject

class DeleteProdutoUseCase @Inject constructor(
    private val repository: ProdutoRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return repository.deleteProduto(id)
    }
}