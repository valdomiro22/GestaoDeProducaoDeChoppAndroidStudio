package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.usecase

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.repository.ProducaoRepository
import java.util.UUID
import javax.inject.Inject

class InsertProducaoUseCase @Inject constructor(
    private val repository: ProducaoRepository
) {
    suspend operator fun invoke(producao: ProducaoEntity): Result<Unit> {
        val idGerado = UUID.randomUUID().toString()
        val producaoComId = producao.copy(id = idGerado)
        return repository.insertProducao(producaoComId)
    }
}