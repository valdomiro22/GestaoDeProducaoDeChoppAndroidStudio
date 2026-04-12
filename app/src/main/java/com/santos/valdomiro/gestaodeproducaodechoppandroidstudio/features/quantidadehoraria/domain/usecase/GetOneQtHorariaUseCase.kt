package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.QuantidadeHorariaEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.repository.QuantidadeHorariaRepository
import javax.inject.Inject

class GetOneQtHorariaUseCase @Inject constructor(
    private val repository: QuantidadeHorariaRepository
) {
    suspend operator fun invoke(id: String): Result<QuantidadeHorariaEntity> {
        return repository.getQtHoraria(id = id).fold(
            onSuccess = { qtHoraria ->
                if (qtHoraria != null) {
                    Result.success(qtHoraria)
                } else {
                    Result.failure(Exception("Quantidade Horária não encontrada com o ID: $id"))
                }
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }
}