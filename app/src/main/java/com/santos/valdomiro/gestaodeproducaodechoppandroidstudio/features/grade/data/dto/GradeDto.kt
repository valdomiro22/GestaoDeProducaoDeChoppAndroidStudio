package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.dto

import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

data class GradeDto @OptIn(ExperimentalTime::class) constructor(
    val id: String?,
    val numero: Int = 0,
    val data: Instant = Clock.System.now(),
    val quantidadeBarris: Int = -1,
    val volumeHlNecessario: Double = -0.1,
) {

    @OptIn(ExperimentalTime::class)
    fun toMap() : Map<String, Any?> {
        return mapOf(
            "id" to id,
            "numero" to numero,
            "data" to data,
            "quantidadeBarris" to quantidadeBarris,
            "volumeHlNecessario" to volumeHlNecessario,
        )
    }

}