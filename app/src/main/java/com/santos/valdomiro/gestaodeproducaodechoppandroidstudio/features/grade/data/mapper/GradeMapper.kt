package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.mapper

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.data.dto.GradeRemoteDto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.grade.domain.entity.GradeEntity
import kotlin.time.ExperimentalTime

import java.time.ZoneId
import java.util.Date

// LENDO DO BANCO: Converte o Date do Firebase de volta para LocalDate (UTC)
fun GradeRemoteDto.toEntity(): GradeEntity {
    // Pega o Date do Firebase e garante que ele seja lido no fuso UTC para não pular dias
    val localDateRecuperado = this.data.toInstant()
        .atZone(ZoneId.of("UTC"))
        .toLocalDate()

    return GradeEntity(
        id = this.id,
        numero = this.numero,
        data = localDateRecuperado, // Agora sua Entity tem o LocalDate perfeito
        quantidadeBarris = this.quantidadeBarris,
        volumeHlNecessario = this.volumeHlNecessario,
    )
}

// SALVANDO NO BANCO: Converte o LocalDate da Entity para o Date do Firebase (UTC)
fun GradeEntity.toDto(): GradeRemoteDto {
    // Força meia-noite em UTC antes de converter para Date
    val instante = this.data.atStartOfDay(ZoneId.of("UTC")).toInstant()
    val dataConvertidaParaFirebase = Date.from(instante)

    return GradeRemoteDto(
        id = this.id,
        numero = this.numero,
        data = dataConvertidaParaFirebase, // Enviando como Date para o Firestore
        quantidadeBarris = this.quantidadeBarris,
        volumeHlNecessario = this.volumeHlNecessario,
    )
}