package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.dto

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.StatusProducao
import java.util.Date

data class ProducaoRemoteDto(
    val id: String? = "",
    val gradeId: String = "",
    val status: StatusProducao = StatusProducao.NAO_CONCLUIDA,
    val barrilId: String = "",
    val produtoId: String = "",
    val quantidadeProgramada: Int = 0,
    val quantidadeProduzida: Int = 0,
    val dataCriacao: Date = Date(),
    val dataFimDeProducao: Date? = null,
)