package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity

import java.time.LocalDate

data class GradeEntity constructor(
    val id: String? = null,
    val numero: Int = 0,
    val data: LocalDate = LocalDate.now(),
    val quantidadeBarris: Int = -1,
    val volumeHlNecessario: Double = -0.1,
)
