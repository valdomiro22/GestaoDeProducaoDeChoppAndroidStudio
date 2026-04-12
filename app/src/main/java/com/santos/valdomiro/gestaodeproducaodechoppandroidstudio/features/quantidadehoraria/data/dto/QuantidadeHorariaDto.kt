package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.data.dto

import java.util.Date

data class QuantidadeHorariaDto(
    val id: String = "",
    val turnoId: Int = 1,
    val producaoId: String = "",
    val horarioReferente: Int = 0,
    val quantidade: Int = -1,
    val quantidadeAcumulada: Int = -1,
    val horario: Date = Date(),
    val data: Date = Date()
)