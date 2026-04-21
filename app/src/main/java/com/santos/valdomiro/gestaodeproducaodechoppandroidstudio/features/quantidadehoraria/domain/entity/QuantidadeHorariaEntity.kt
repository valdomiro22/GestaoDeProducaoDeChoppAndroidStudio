package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity

import java.time.LocalDate

data class QuantidadeHorariaEntity(
    val id: String? = null,
    val turnoId: Int = 1,
    val producaoId: String = "",
    val horarioReferente: String = "",
    val quantidade: Int = -1,
    val quantidadeAcumulada: Int = -1,
    val horario: LocalDate = LocalDate.now(),
    val data: LocalDate = LocalDate.now(),
)