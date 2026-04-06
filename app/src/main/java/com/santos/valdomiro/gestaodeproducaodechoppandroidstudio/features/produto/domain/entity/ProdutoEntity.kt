package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.domain.entity

data class ProdutoEntity(
    val id: String? = null,
    val nome: String = "",
    val prazoValidade: Int = 0,
)