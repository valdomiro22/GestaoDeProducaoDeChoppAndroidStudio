package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.repository.QuantidadeHorariaRepository
import javax.inject.Inject

class DeleteQtHorariaUseCase @Inject constructor(
    private val repository: QuantidadeHorariaRepository
) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return repository.deleteQtHoraria(id = id)
    }
}