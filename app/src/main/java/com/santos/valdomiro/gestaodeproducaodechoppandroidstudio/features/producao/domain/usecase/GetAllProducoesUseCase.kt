package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.repository.ProducaoRepository
import javax.inject.Inject

class GetAllProducoesUseCase @Inject constructor(
    private val repository: ProducaoRepository
) {
    suspend operator fun invoke(): Result<List<ProducaoEntity>> {
        return repository.getAllProducoes()
    }
}