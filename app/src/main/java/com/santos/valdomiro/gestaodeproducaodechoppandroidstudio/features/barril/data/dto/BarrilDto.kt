package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.barril.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BarrilDto(
    val id: String? = null,
    val nome: String = "",
    val volume: Int = -1,
    @SerialName("is_descartavel") val isDescartavel: Boolean = false
) {

    /** Converte o DTO para Map usado no .update() do Firestore */
    fun toMap() : Map<String, Any?> {
        return mapOf(
            "nome" to nome,
            "volume" to volume,
            "isDescartavel" to isDescartavel,
        )
    }
}