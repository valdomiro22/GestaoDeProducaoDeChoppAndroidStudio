package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.data.mapper

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.mapper.toFirestoreDate
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.mapper.toLocalDate
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.data.dto.QuantidadeHorariaDto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.quantidadehoraria.domain.entity.QuantidadeHorariaEntity

fun QuantidadeHorariaDto.toEntity(): QuantidadeHorariaEntity {
    return QuantidadeHorariaEntity(
        id = this.id,
        turnoId = this.turnoId,
        producaoId = this.producaoId,
        horarioReferente = this.horarioReferente,
        quantidade = this.quantidade,
        quantidadeAcumulada = this.quantidadeAcumulada,
        horario = this.horario.toLocalDate(),
        data = this.data.toLocalDate()
    )
}

fun QuantidadeHorariaEntity.toDto(): QuantidadeHorariaDto {
    return QuantidadeHorariaDto(
        id = this.id,
        turnoId = this.turnoId,
        producaoId = this.producaoId,
        horarioReferente = this.horarioReferente,
        quantidade = this.quantidade,
        quantidadeAcumulada = this.quantidadeAcumulada,
        horario = this.horario.toFirestoreDate(),
        data = this.data.toFirestoreDate()
    )
}