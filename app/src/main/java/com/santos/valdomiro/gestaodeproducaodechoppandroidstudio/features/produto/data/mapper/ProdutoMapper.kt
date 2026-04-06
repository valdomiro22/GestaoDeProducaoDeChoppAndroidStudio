package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.mapper

import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.dto.ProdutoDto
import com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity.ProdutoEntity

fun ProdutoDto.toEntity() = ProdutoEntity(
    id = this.id,
    nome = this.nome,
    prazoValidade = this.prazoValidade
)

fun ProdutoEntity.toDto() = ProdutoDto(
    id = this.id,
    nome = this.nome,
    prazoValidade = this.prazoValidade
)