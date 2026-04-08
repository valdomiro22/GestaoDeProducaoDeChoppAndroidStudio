package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.repository.ProdutoRepository
import javax.inject.Inject

class GetAllProdutosUseCase @Inject constructor(
    private val repository: ProdutoRepository
) {
    suspend operator fun invoke(): Result<List<ProdutoEntity>> {
        return repository.getAllProdutos()
    }
}