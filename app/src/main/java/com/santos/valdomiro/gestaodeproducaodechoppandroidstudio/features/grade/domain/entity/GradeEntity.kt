package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity

import com.google.type.DateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class GradeEntity @OptIn(ExperimentalTime::class) constructor(
    val id: String?,
    val numero: Int = 0,
    val data: Instant = Clock.System.now(),
    val quantidadeBarris: Int = -1,
    val volumeHlNecessario: Double = -0.1,
)
