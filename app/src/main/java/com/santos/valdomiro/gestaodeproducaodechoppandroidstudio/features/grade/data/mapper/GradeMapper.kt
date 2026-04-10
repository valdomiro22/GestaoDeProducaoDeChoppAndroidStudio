package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.mapper

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.dto.GradeRemoteDto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import kotlin.time.ExperimentalTime

fun GradeRemoteDto.toEntity() = GradeEntity(
    id = this.id,
    numero = this.numero,
    data = this.data,
    quantidadeBarris = this.quantidadeBarris,
    volumeHlNecessario = this.volumeHlNecessario,
)

@OptIn(ExperimentalTime::class)
fun GradeEntity.toDto() = GradeRemoteDto(
    id = this.id,
    numero = this.numero,
    data = this.data,
    quantidadeBarris = this.quantidadeBarris,
    volumeHlNecessario = this.volumeHlNecessario,
)