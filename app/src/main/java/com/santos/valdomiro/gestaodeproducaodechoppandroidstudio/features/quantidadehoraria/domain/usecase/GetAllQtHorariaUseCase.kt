package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.QuantidadeHorariaEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.repository.QuantidadeHorariaRepository
import javax.inject.Inject

class GetAllQtHorariaUseCase @Inject constructor(
    private val repository: QuantidadeHorariaRepository
) {
    suspend operator fun invoke(): Result<List<QuantidadeHorariaEntity>> {
        return repository.getAllQtHorarias()
    }
}