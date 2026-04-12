package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity

import java.time.LocalDate

data class ProducaoEntity(
    val id: String? = "",
    val gradeId: String = "",
    val barrilId: String = "",
    val barrilNome: String = "",
    val produtoId: String = "",
    val produtoNome: String = "",
    val status: StatusProducao = StatusProducao.NAO_CONCLUIDA,
    val quantidadeProgramada: Int = 0,
    val quantidadeProduzida: Int = 0,
    val dataCriacao: LocalDate = LocalDate.now(),
    val dataFimDeProducao: LocalDate? = null,
)