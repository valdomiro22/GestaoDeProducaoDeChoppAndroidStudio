package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.mapper

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.dto.GradeDto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun GradeDto.toEntity() = GradeEntity(
    id = this.id,
    numero = this.numero,
    data = this.data,
    quantidadeBarris = this.quantidadeBarris,
    volumeHlNecessario = this.volumeHlNecessario,
)

@OptIn(ExperimentalTime::class)
fun GradeEntity.toDto() = GradeDto(
    id = this.id,
    numero = this.numero,
    data = this.data,
    quantidadeBarris = this.quantidadeBarris,
    volumeHlNecessario = this.volumeHlNecessario,
)