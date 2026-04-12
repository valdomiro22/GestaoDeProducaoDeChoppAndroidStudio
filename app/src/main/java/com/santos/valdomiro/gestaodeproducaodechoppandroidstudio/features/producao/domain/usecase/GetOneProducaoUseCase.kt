package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.repository.ProducaoRepository
import javax.inject.Inject

class GetOneProducaoUseCase @Inject constructor(
    private val repository: ProducaoRepository
) {
    suspend operator fun invoke(id: String): Result<ProducaoEntity> {
        return repository.getProducao(id = id).fold(
            onSuccess = { producao ->
                if (producao != null) {
                    Result.success(producao)
                } else {
                    Result.failure(Exception("Produção não encontrada com o ID: $id"))
                }
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}