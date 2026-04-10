package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.dto

import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import kotlin.time.ExperimentalTime

data class GradeRemoteDto @OptIn(ExperimentalTime::class) constructor(
    val id: String? = null,
    val numero: Int = 0,
    val data: Date = Date(),
    val quantidadeBarris: Int = -1,
    val volumeHlNecessario: Double = -0.1,
)