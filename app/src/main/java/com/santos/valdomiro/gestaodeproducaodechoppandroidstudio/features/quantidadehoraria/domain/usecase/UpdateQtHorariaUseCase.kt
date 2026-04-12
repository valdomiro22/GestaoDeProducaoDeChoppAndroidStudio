package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.QuantidadeHorariaEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.repository.QuantidadeHorariaRepository
import javax.inject.Inject

class UpdateQtHorariaUseCase @Inject constructor(
    private val repository: QuantidadeHorariaRepository
) {
    suspend operator fun invoke(id: String, qtHoraria: QuantidadeHorariaEntity): Result<Unit> {
        return repository.updateQtHoraria(id = id, qtHoraria = qtHoraria)
    }
}