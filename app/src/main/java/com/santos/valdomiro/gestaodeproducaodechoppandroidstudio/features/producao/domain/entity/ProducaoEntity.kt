package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity

import java.time.LocalDate

data class ProducaoEntity(
    val id: String? = "",
    val gradeId: String = "",
    val status: StatusProducao = StatusProducao.NAO_CONCLUIDA,
    val barrilId: String = "",
    val produtoId: String = "",
    val quantidadeProgramada: Int = -1,
    val quantidadeProduzida: Int = -1,
    val dataCriacao: LocalDate = LocalDate.now(),
    val dataFimDeProducao: LocalDate? = null,
)