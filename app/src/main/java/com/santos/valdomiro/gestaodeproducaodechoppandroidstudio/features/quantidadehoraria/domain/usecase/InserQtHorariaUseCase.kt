package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.QuantidadeHorariaEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.repository.QuantidadeHorariaRepository
import java.util.UUID
import javax.inject.Inject

class InserQtHorariaUseCase @Inject constructor(
    private val repository: QuantidadeHorariaRepository
) {
    suspend operator fun invoke(qtHoraria: QuantidadeHorariaEntity): Result<Unit> {
        val idGerado = UUID.randomUUID().toString()
        val qtHorariaComId = qtHoraria.copy(id = idGerado)
        return repository.insertQtHoraria(qtHorariaComId)
    }
}