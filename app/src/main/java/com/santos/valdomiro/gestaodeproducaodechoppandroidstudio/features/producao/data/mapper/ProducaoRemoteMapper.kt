package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.mapper

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.mapper.toFirestoreDate
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.common.mapper.toLocalDate
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.data.dto.ProducaoRemoteDto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity.ProducaoEntity

fun ProducaoRemoteDto.toEntity(): ProducaoEntity {
    return ProducaoEntity(
        id = this.id,
        gradeId = this.gradeId,
        status = this.status,
        barrilId = this.barrilId,
        produtoId = this.produtoId,
        quantidadeProgramada = this.quantidadeProgramada,
        quantidadeProduzida = this.quantidadeProduzida,
        dataCriacao = this.dataCriacao.toLocalDate(),
        dataFimDeProducao = this.dataFimDeProducao?.toLocalDate()
    )
}

fun ProducaoEntity.toDto(): ProducaoRemoteDto {
    return ProducaoRemoteDto(
        id = this.id,
        gradeId = this.gradeId,
        status = this.status,
        barrilId = this.barrilId,
        produtoId = this.produtoId,
        quantidadeProgramada = this.quantidadeProgramada,
        quantidadeProduzida = this.quantidadeProduzida,
        dataCriacao = this.dataCriacao.toFirestoreDate(),
        dataFimDeProducao = this.dataFimDeProducao?.toFirestoreDate()
    )
}