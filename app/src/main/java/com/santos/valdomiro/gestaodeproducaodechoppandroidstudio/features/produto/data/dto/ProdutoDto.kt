package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.produto.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProdutoDto(
    val id: String? = null,
    val nome: String = "",
    val prazoValidade: Int = 0,
) {

    fun toMap() : Map<String, Any?> {
        return mapOf(
            "nome" to nome,
            "prazoValidade" to prazoValidade,
        )
    }

}
