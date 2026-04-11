package com.santos.valdomiro.gestaodeproducaodechoppandroidstudio.features.producao.domain.entity

enum class StatusProducao(val id: Int, val label: String) {
    CONCLUIDA(1, "Concluida"),
    NAO_CONCLUIDA(2, "Não concluida");

    companion object {
        fun fromId(id: Int): StatusProducao {
            return entries.firstOrNull { it.id == id }
                ?: throw IllegalArgumentException("Id de StatusProducao inválido: $id")
        }
    }
}