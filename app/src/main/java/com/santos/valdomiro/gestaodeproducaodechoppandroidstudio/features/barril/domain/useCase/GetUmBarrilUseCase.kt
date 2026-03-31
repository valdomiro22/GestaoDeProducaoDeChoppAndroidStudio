package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.domain.useCase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.domain.entity.BarrilEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.domain.repository.BarrilRepository
import javax.inject.Inject

class GetUmBarrilUseCase @Inject constructor(
    private val barrilRepository: BarrilRepository
) {

    suspend operator fun invoke(id: String): Result<BarrilEntity?> {
        return barrilRepository.getBarril(id)
    }

}