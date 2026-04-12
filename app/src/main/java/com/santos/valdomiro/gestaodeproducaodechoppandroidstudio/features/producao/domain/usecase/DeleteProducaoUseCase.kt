package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.repository.ProducaoRepository
import javax.inject.Inject

class DeleteProducaoUseCase @Inject constructor(
    private val repository: ProducaoRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return  repository.deleteProducao(id = id)
    }
}