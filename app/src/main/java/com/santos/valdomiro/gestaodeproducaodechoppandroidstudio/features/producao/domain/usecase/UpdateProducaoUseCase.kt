package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.repository.ProducaoRepository
import javax.inject.Inject

class UpdateProducaoUseCase @Inject constructor(
    private val repository: ProducaoRepository
) {
    suspend operator fun invoke(id: String, producao: ProducaoEntity): Result<Unit> {
        return repository.updateProducao(id = id, producao = producao)
    }
}