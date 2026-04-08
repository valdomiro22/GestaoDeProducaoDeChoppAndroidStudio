package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.repository.ProdutoRepository
import javax.inject.Inject

class GetUmProdutoUseCase @Inject constructor(
    private val repository: ProdutoRepository
) {
    suspend operator fun invoke(id: String): Result<ProdutoEntity> {
        return repository.getProduto(id).fold(
            onSuccess = { produto ->
                if (produto != null) {
                    Result.success(produto)
                } else {
                    Result.failure(Exception("Produto não encontrado com o ID: $id"))
                }
            },
            onFailure = { exception ->
                Result.failure(exception)
            }
        )
    }
}