package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.dto

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import kotlin.time.ExperimentalTime

data class GradeRemoteDto @OptIn(ExperimentalTime::class) constructor(
    val id: String? = null,
    val numero: Int = 0,
    val data: LocalDate = LocalDate.now(),
    val quantidadeBarris: Int = -1,
    val volumeHlNecessario: Double = -0.1,
) {

    fun toMap(): Map<String, Any?> {
        val instante = data.atStartOfDay(ZoneId.of("UTC")).toInstant()
        val dataTimestamp = Timestamp(Date.from(instante))

        return mapOf(
            "id" to id,
            "numero" to numero,
            "data" to dataTimestamp,
            "quantidadeBarris" to quantidadeBarris,
            "volumeHlNecessario" to volumeHlNecessario,
        )
    }

}